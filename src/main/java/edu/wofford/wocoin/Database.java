package edu.wofford.wocoin;
import java.io.*;
import java.sql.*;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.io.FileUtils;
import org.web3j.crypto.CipherException;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.crypto.WalletUtils;
import java.security.*;
import java.util.List;
import java.util.ArrayList;

public class Database {
    private String adminPwd;
    private String url;
    private Connection con;
    private Web3j web3;
    private String address;

    /**
     * This is the constructor for db
     *
     * @param fileName relative pathname of database to be created
     */
    public Database(String fileName) {
        adminPwd = "adminpwd";
        url = "jdbc:sqlite:" + fileName;

        File file = new File(fileName);
        if(!file.exists()){
            Utilities.createNewDatabase(fileName);

        }

    }

    /**
     * Returns the administrator password
     *
     * @return the administrators password
     */

    public String getAdminPwd() {
        return adminPwd;
    }

    /**
     * Checks if administrator is valid
     *
     * @param password - user input to check against
     * @return boolean if the password exists or not
     */

    public boolean checkIsAdmin(String password) {
        if (password.equals(getAdminPwd())) {
            return true;
        } else {
            return false;
        }


    }
    /**
     * Checks if user already exists in the table
     *
     * @param id username
     * @return boolean if the user exists
     */

    public boolean userExists(String id) {


        // String testQuery = "SELECT id FROM users WHERE id = ?;";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement("SELECT id FROM users WHERE id = ?;")) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                if (rs.getString(1).equals(id)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * Adds user to the table
     *
     * @param id       this will be the value stored in the id column of the Database
     * @param password this value will be salted and hashed and stored in the salt and hash columns of the DB
     * @return boolean if user exists or not
     */

    public boolean addUser(String id, String password) {
        if (!userExists(id)) {
            String saltedPasswd;
            int salt = generateSalt();
            saltedPasswd = getSaltedPasswd(password, salt);
            String hash = getHash(saltedPasswd);

            String testQuery = "INSERT INTO users (id, salt, hash) VALUES (?, ?, ?);";

            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement stmt = conn.prepareStatement(testQuery)) {

                stmt.setString(1, id);
                stmt.setInt(2, salt);
                stmt.setString(3, hash);
                stmt.executeUpdate();


                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }

    }
    /**
     * Removes a user identified by their id from the Database
     *
     * @param id a value passed in by the user to be removed
     * @return a boolean value of if the user was removed or not
     */
    public boolean removeUser(String id) {
        if (userExists(id)) {
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE id = ?;")){
                stmt.setString(1,id);
                stmt.executeUpdate();

                return true;
            } catch(SQLException e) {
                e.printStackTrace();
                return false; }
        } else {
            return false;
        }

    }

    /**
     * Salts the username
     * @return a random salt value
     */
    private int generateSalt(){
        int saltValue = Utilities.generateSalt();
        return saltValue;
    }

    /**
     * Creates salted password
     * @param passWd - the plain text password passed into the functions initially
     * @param saltValue - a random int that is converted to a string
     * @return passWd concatenated with SaltValue
     */

    private String getSaltedPasswd(String passWd, int saltValue){

        String SaltValueString = Integer.toString(saltValue);
        String builder = passWd + SaltValueString;
        return builder;

    }

    /**
     * Creates hash of salted password
     * @param saltedPasswd this is the value returned from the above function
     * @return a string that will be stored in the hash field of the DB
     */
    private String getHash(String saltedPasswd) {
        return Utilities.applySha256(saltedPasswd);
    }


    /**
     * Checks to see if the user has a wallet in the table
     * @param id the name of the user
     * @return boolean of if the user has a wallet
     */
    public boolean walletExists(String id){
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement("SELECT id FROM wallets WHERE id = ?;")){

            stmt.setString(1,id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                if(rs.getString(1).equals(id)){
                    return true;
                }
                else{
                    return false;
                }
            } else {
                return false;
            }

        }
        catch(SQLException e){
            e.printStackTrace();
            return true;
        }
    }

    /**
     * Creates a wallet for the user
     * @param id this is the parameter that reprent's username
     * @return whether or not if the wallet was created.
     */

    public boolean createWallet(String id) {

        if (!userExists(id)) {
            return false;
        }
        else{
            if (walletExists(id)) {
                try {
                    String directoryString = "tmp//" + id + "//";
                    File directory = new File(directoryString);
                    FileUtils.cleanDirectory(directory);
                    directory.delete();
                }
                catch(IOException | IllegalArgumentException e){
                    System.out.println("error");
                }

                    String destinationDir = "tmp//" + id + "//";
                    File destination = new File(destinationDir);
                    destination.mkdirs();


                    try {
                        web3 = Web3j.build(new HttpService("https://mainnet.infura.io/v3/338a115fa5324abeadccd992f9c6cbab"));

                        String walletFileName = WalletUtils.generateFullNewWalletFile("password", destination);
                        //Credentials credentials = WalletUtils.loadCredentials("password", "/ethereum/node0/keystore/UTC--2019-08-07T17-24-10.532680697Z--0fce4741f3f54fbffb97837b4ddaa8f769ba0f91.json");
                        //File f2 = new File(destinationDir + walletFileName);

                        String[] fetchAddress = walletFileName.split("--");

                        String getAddress = fetchAddress[fetchAddress.length - 1].split("\\.")[0];
                        address =getAddress;
                    }
                    catch (NoSuchAlgorithmException e) {
                        System.out.println("exception");
                        e.printStackTrace();
                        return false;
                    } catch (SecurityException | GeneralSecurityException | IOException | CipherException e) {
                        e.printStackTrace();
                        return false;
                    }



                    try (Connection conne = DriverManager.getConnection(url);
                         PreparedStatement stmt = conne.prepareStatement("DELETE FROM wallets WHERE id = ?;")){
                        stmt.setString(1,id);
                        stmt.executeUpdate();


                    }
                    catch(SQLException e) {
                        e.printStackTrace();
                    }

                    String testQuery = "INSERT INTO wallets (id, publickey) VALUES (?, ?);";


                    try (Connection conn = DriverManager.getConnection(url);
                         PreparedStatement stmt = conn.prepareStatement(testQuery)) {

                        stmt.setString(1, id);
                        stmt.setString(2, address);
                        stmt.executeUpdate();
                        return true;


                    } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                    }


            } else {
                String destinationDir = "tmp//" + id + "//";
                File destination = new File(destinationDir);
                destination.mkdirs();

                try {
                    web3 = Web3j.build(new HttpService("https://mainnet.infura.io/v3/338a115fa5324abeadccd992f9c6cbab"));
                    String walletFileName = WalletUtils.generateFullNewWalletFile("password", destination);
                    String[] fetchAddress = walletFileName.split("--");

                    String getAddress = fetchAddress[fetchAddress.length - 1].split("\\.")[0];
                    address = getAddress;
                }

                catch (NoSuchAlgorithmException e) {
                    System.out.println("exception");
                    e.printStackTrace();
                    return false;
                } catch (SecurityException | GeneralSecurityException | IOException | CipherException e) {
                    e.printStackTrace();
                    return false;
                }

                    String testQuery = "INSERT INTO wallets (id, publickey) VALUES (?, ?);";


                    try (Connection conn = DriverManager.getConnection(url);
                         PreparedStatement stmt = conn.prepareStatement(testQuery)) {

                        stmt.setString(1, id);
                        stmt.setString(2, address);
                        stmt.executeUpdate();
                        return true;



                    } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                    }


            }


        }


    }

    private String turnPublicKeyToId(String publicKey){
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM wallets WHERE publickey = ?;")){

            stmt.setString(1,publicKey);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                if(rs.getString(2).equals(publicKey)){
                    String builder =  rs.getString(1);
                    return builder;
                }
                else{
                    return "";
                }
            } else {
                return "";
            }

        }
        catch(SQLException e){
            e.printStackTrace();
            return "";
        }
    }

    public String turnIdtoPublickey(String id){
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM wallets WHERE id = ?;")){

            stmt.setString(1,id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                if(rs.getString(1).equals(id)){
                    return rs.getString(2);
                }
                else{
                    return "";
                }
            } else {
                return "";
            }

        }
        catch(SQLException e){
            e.printStackTrace();
            return "";
        }
    }

    private boolean isValidName(String name){
        return !name.equals("");
    }

    private boolean isValidPrice(int price){
        return price > 0;
    }


    private boolean isValidDescription(String description){
        return !description.equals("");
    }

    /**
     * Adds a product to the table
     * @param seller this is the public key found in the wallets table of the seller of the product
     * @param price this is the amount of Wocoins a product costs.
     * @param name this is the name of the product.
     * @param description this is a user defined description of the product.
     * @return whether or not the product was added.
     */
    public boolean addProduct(String seller, int price, String name, String description){
        String id = turnPublicKeyToId(seller);

        if(walletExists(id)){
            if(isValidName(name) && isValidPrice(price) && isValidDescription(description)){

                String testQuery = "INSERT INTO products (seller, price, name, description) VALUES (?, ?, ?, ?);";

                try (Connection conn= DriverManager.getConnection(url);
                     PreparedStatement stmt = conn.prepareStatement(testQuery)){

                    stmt.setString(1, seller);
                    stmt.setInt(2, price);
                    stmt.setString(3, name);
                    stmt.setString(4, description);
                    stmt.executeUpdate();
                    return true;
                }
                catch(SQLException e){
                    e.printStackTrace();
                    return false;
                }

            }else{
                return false;
            }
        } else {
            return false;
        }

    }

    /**
     * Removes a product identified by their id from the Database
     * @param name a value passed in by the product to be removed
     * @return a boolean value of if the product was removed or not
     */
    public boolean removeProduct(String name){
        try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM product WHERE name = ?;")){
                stmt.setString(1, name);
                stmt.executeUpdate();

                return true;
            } catch(SQLException e) {
                e.printStackTrace();
                return false;
            }
//        if(walletExists(id)){
//            try (Connection conn = DriverManager.getConnection(url);
//                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM product WHERE name = ?;")){
//                stmt.setString(1, name);
//                stmt.executeUpdate();
//
//                return true;
//            } catch(SQLException e) {
//                e.printStackTrace();
//                return false; }
//        } else {
//            return false;
//        }

    }

    /**
     * Displays the product
     * @return a string of all of the products
     */


    public List<Product> displayProductF6() {
        List<Product> list = new ArrayList<Product>();
        list.add(0, null);
        try(Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from products order by name collate nocase;");
            ResultSet rsCount = stmt.executeQuery("select count(*) from products;");
            rs.next();
            for (int i = 1; i <= rsCount.getInt(1); i++) {
                Product p = new Product(rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5));
                list.add(p);
                rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*for (int i = 0; i <= 9; i++){
            sList.add(productList);

        }*/
        return list;
    }
//write unit test


    /**
     *
     * @param seller
     * @return
     */

    public List displayProductF5(String seller){
        List<Product> list = new ArrayList<Product>();
        list.add(0, null);
        String query = "select *, count(*) over () total_rows from products where seller = ? order by name collate nocase;";
        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, seller);
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            int rows = rs.getInt(6);
            for(int i=1; i <= rows; i++){
                Product p = new Product(rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5));
                list.add(p);
                rs.next();
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //write unit tests

    /**
     * Determines if the password is correct
     * @param username of the person querying the db
     * @param password of the person querying the db
     * @return whether or not if the password is correct g
     */

    public boolean passwordCorrect(String username, String password){
        return true;
    }



    public String Carrats(String id){
        String builder = "";
        String carrats;
        String wocoin;


        if(userExists(id)){
            //String key = getPublicKey(id);

            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("select *, count(*) over () total_rows from products order by price, name collate nocase;");


                rs.next();
                int rows = rs.getInt(6);

                for(int i=1; i <= rows; i++){

                    if(rs.getString(2).equals(turnIdtoPublickey(id))){
                        carrats = ">>>  ";
                    }
                    else {
                        carrats = "";
                    }
                    rs.next();
                }


                return builder;
    }


//    public String Carrats(String id){
//        String builder = "";
//        String carrats;
//        String wocoin;
//
//
//        if(userExists(id)) {
//            //String key = getPublicKey(id);
//
//            try (Connection conn = DriverManager.getConnection(url);
//                 Statement stmt = conn.createStatement()) {
//                ResultSet rs = stmt.executeQuery("select *, count(*) over () total_rows from products order by price, name collate nocase;");
//
//
//                rs.next();
//                int rows = rs.getInt(6);
//
//                for (int i = 1; i <= rows; i++) {
//
//                    if (rs.getString(2).equals(turnIdtoPublickey(id))) {
//                        carrats = ">>>  ";
//                    } else {
//                        carrats = "";
//                    }
//                    rs.next();
//                }
//
//
//                return builder;
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//
//
//        /*String builder = "";
//        String carrats;
//        String wocoin;
//
//
//        if(userExists(id)){
//            //String key = getPublicKey(id);
//
//            try (Connection conn = DriverManager.getConnection(url);
//                 Statement stmt = conn.createStatement()) {
//                ResultSet rs = stmt.executeQuery("select *, count(*) over () total_rows from products order by price, name collate nocase;");
//
//
//                rs.next();
//                int rows = rs.getInt(6);
//
//                for(int i=1; i <= rows; i++){
//
//                    if(rs.getString(2).equals(turnIdtoPublickey(id))){
//                        carrats = ">>>  ";
//                    }
//                    else {
//                        carrats = "";
//                    }
//
//
//
//
//                    if(rs.getInt(3) == 1){
//                        wocoin = "WoCoin]";
//
//                    }
//                    else{
//                        wocoin= "WoCoins]";
//                    }
//
//                    builder += i + ": " + carrats + rs.getString(4) + ": " + rs.getString(5) + "  " + "[" + Integer.toString(rs.getInt(3)) + " " + wocoin + "\r\n";
//                    rs.next();
//                }
//
//
//                return builder;
//
//
//
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//                return "e";
//            }
//
//
//            // check if products showing were added by user
//
//            // 2D string array of size 4 to capture >>>, name, display, and price
//            // menu uses string array to print a formatted string using the individual elements
//
//        }else{
//
//            String result ="No such user.";
//            return result;
//        }*/
//
//}
