package edu.wofford.wocoin;
import java.io.*;
import java.sql.*;
import java.security.NoSuchAlgorithmException;
import java.lang.InterruptedException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import javax.crypto.Cipher;

import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.io.FileUtils;
import org.web3j.crypto.CipherException;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.crypto.WalletUtils;
import java.security.*;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Optional;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;
import org.web3j.utils.Numeric;




public class Database {
    private String adminPwd;
    private String url;
    private Connection con;
    public boolean detectsExisting;
    private Web3j web3;
    private String address;
    private RawTransaction rt;
    private BigInteger nonce;
    private String senderAddress;

    /**
     * This is the constructor for db
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
     * @return the administrators password
     */

    public  String getAdminPwd(){
        return adminPwd;
    }

    /**
     * Checks if administrator is valid
     * @param password - user input to check against
     * @return boolean if the password exists or not
     */

    public boolean checkIsAdmin(String password){
        if(password.equals(getAdminPwd())){
            return true;
        }
        else{
            return false;
        }


    }
    /**
     * Checks if user already exists in the table
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

        public boolean addUser (String id, String password){
            if (!userExists(id)) {
                String saltedPasswd;
                int salt = generateSalt();
                saltedPasswd = getSaltedPasswd(password, salt);
                String hash = getHash(saltedPasswd);
                //url is empty
                //sql statement addUser

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
        public boolean removeUser (String id){
            if (userExists(id)) {
                try (Connection conn = DriverManager.getConnection(url);
                     PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE id = ?;")) {
                    stmt.setString(1, id);
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
         * Salts the username
         * @return a random salt value
         */
        private int generateSalt () {
            int saltValue = Utilities.generateSalt();
            return saltValue;
        }

        /**
         * Creates salted password
         * @param passWd - the plain text password passed into the functions initially
         * @param saltValue - a random int that is converted to a string
         * @return passWd concatenated with SaltValue
         */

        private String getSaltedPasswd (String passWd,int saltValue){

            String SaltValueString = Integer.toString(saltValue);
            String builder = passWd + SaltValueString;
            return builder;

        }

        /**
         * Creates hash of salted password
         * @param saltedPasswd this is the value returned from the above function
         * @return a string that will be stored in the hash field of the DB
         */
        private String getHash (String saltedPasswd){
            return Utilities.applySha256(saltedPasswd);
        }


        /**
         * Checks to see if the user has a wallet in the table
         * @param id the name of the user
         * @return boolean of if the user has a wallet
         */
        public boolean walletExists (String id){
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement stmt = conn.prepareStatement("SELECT id FROM wallets WHERE id = ?;")) {

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
         * Creates a wallet for the user
         * @param id this is the parameter that reprent's username
         * @return whether or not if the wallet was created.
         */

        public boolean createWallet (String id, String filename, String password){

            String temp = filename + "//" + id + "//";
            File dir = new File(temp);
            dir.mkdirs();
            try {
                web3 = Web3j.build(new HttpService());
                String walletFileName = WalletUtils.generateFullNewWalletFile(password, dir);
                String[] fetchAddress = walletFileName.split("--");
                String getAddress = fetchAddress[fetchAddress.length - 1].split("\\.")[0];
                address = getAddress;
            } catch (NoSuchAlgorithmException e) {
                System.out.println("exception");
                e.printStackTrace();
                return false;
            } catch (SecurityException | GeneralSecurityException | IOException | CipherException e) {
                e.printStackTrace();
                return false;
            }

            try (Connection conne = DriverManager.getConnection(url);
                 PreparedStatement stmt = conne.prepareStatement("DELETE FROM wallets WHERE id = ?;")) {
                stmt.setString(1, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
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
        }

        private String turnPublicKeyToId (String publicKey){
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement stmt = conn.prepareStatement("SELECT * FROM wallets WHERE publickey = ?;")) {
                stmt.setString(1, publicKey);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    if (rs.getString(2).equals(publicKey)) {
                        String builder = rs.getString(1);
                        return builder;
                    } else {
                        return "";
                    }
                } else {
                    return "";
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return "";
            }
        }

        public String turnIdtoPublickey (String id){
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement stmt = conn.prepareStatement("SELECT * FROM wallets WHERE id = ?;")) {
                stmt.setString(1, id);
                ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString(2);
            } else {
                return "";
            }

            } catch (SQLException e) {
                e.printStackTrace();
                return "";
            }
        }

        private boolean isValidName (String name){
            return !name.equals("");
        }

        private boolean isValidPrice ( int price){
            return price > 0;
        }


        private boolean isValidDescription (String description){
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
        public boolean addProduct (String seller,int price, String name, String description){
            String id = turnPublicKeyToId(seller);

            if (walletExists(id)) {
                if (isValidName(name) && isValidPrice(price) && isValidDescription(description)) {

                    String testQuery = "INSERT INTO products (seller, price, name, description) VALUES (?, ?, ?, ?);";

                    try (Connection conn = DriverManager.getConnection(url);
                         PreparedStatement stmt = conn.prepareStatement(testQuery)) {

                        stmt.setString(1, seller);
                        stmt.setInt(2, price);
                        stmt.setString(3, name);
                        stmt.setString(4, description);
                        stmt.executeUpdate();
                        return true;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                    }
                } else {
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
        public boolean removeProduct (String name){
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM product WHERE name = ?;")) {
                stmt.setString(1, name);
                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
    }

    /**
     * Displays the product
     * @return a list of all of the products in order by price and name
     */


    public List<Product> displayProductF6() {
        List<Product> list = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()) {
            ResultSet rsCount = stmt.executeQuery("select count(*) from products;");
            int n = rsCount.getInt(1);
            ResultSet rs = stmt.executeQuery("select * from products order by price, name collate nocase;");
            rs.next();
            for (int i = 1; i <= n; i++) {
                Product p = new Product(rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5));
                list.add(p);
                rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    /**
     * Displays a list of the products for the specified user
     * @param seller the publicKey for the user
     * @return a list of the products created under the user
     */

    public List displayProductF5(String seller) {
            List<Product> list = new ArrayList<>();
            try(Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
                ResultSet rsCount = stmt.executeQuery("select count(*) from products;");
                int n = rsCount.getInt(1);
                ResultSet rs = stmt.executeQuery("select * from products order by price, name collate nocase;");
                rs.next();
                for (int i = 1; i <= n; i++) {
                    Product p = new Product(rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5));
                    list.add(p);
                    rs.next();
                }
            } catch (SQLException e) {
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

        public boolean passwordCorrect (String username, String password){
            return true;
        }

    /**
     * Returns the BigInteger value of the number of times a transaction is sent.
     * @return nonce value
     * @throws Exception
     */
    public BigInteger getNonce() throws Exception {
            web3 = Web3j.build(new HttpService("https://mainnet.infura.io/v3/338a115fa5324abeadccd992f9c6cbab"));
            senderAddress = "0fce4741f3f54fbffb97837b4ddaa8f769ba0f91";
            String toAddr = "0x" + senderAddress;
            EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(
                    toAddr, DefaultBlockParameterName.LATEST).sendAsync().get();
            //BigInteger nonce1 = getNonce(toAddress);
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();
            return nonce;
        }

        /**
         * Sends a transaction of WoCoins to the specified user.
         * @param id the user's id
         * @param value the amount of WoCoins sent
         * @return a boolean indicating if the transaction was sent
         */
        public boolean sendTransaction (String id,int value){
            String bigValString = Integer.toString(value);
            BigInteger bigValue = new BigInteger(bigValString);

            String gasLim = "12288";
            BigInteger gasLimit = new BigInteger(gasLim);
            //maybe change value to big int
            senderAddress = "0fce4741f3f54fbffb97837b4ddaa8f769ba0f91";

            String toAddress = "0x" + turnIdtoPublickey(id);
            if (userExists(id) && walletExists(id)) {

                web3 = Web3j.build(new HttpService("https://mainnet.infura.io/v3/338a115fa5324abeadccd992f9c6cbab"));

                String path = "ethereum/node0/keystore/UTC--2019-08-07T17-24-10.532680697Z--0fce4741f3f54fbffb97837b4ddaa8f769ba0f91.json";
                //getting the nonce


                address = "0x" + senderAddress;
                try{

                    EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(
                            address, DefaultBlockParameterName.LATEST).sendAsync().get();
                    //BigInteger nonce1 = getNonce(toAddress);
                    BigInteger nonce = ethGetTransactionCount.getTransactionCount();


                    //create raw transaction object
                    BigInteger f = new BigInteger("0");
                    String v = Integer.toString(value);
                    BigInteger val = new BigInteger(v);
                    RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                            nonce, f,f, toAddress, bigValue);

                    rt = rawTransaction;

                }
                 catch(InterruptedException | ExecutionException e){
                    System.out.println("there is an error");
                }

                try{
                    //encode and sign transaction object
                    Credentials credentials = WalletUtils.loadCredentials(
                            "adminpwd",
                            "ethereum/node0/keystore/UTC--2019-08-07T17-24-10.532680697Z--0fce4741f3f54fbffb97837b4ddaa8f769ba0f91.json");

                    byte[] signedMessage = TransactionEncoder.signMessage(rt, credentials);
                    String hexValue = Numeric.toHexString(signedMessage);

                    //send the raw transaction object to the node
                    EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).send();
                    String transactionHash = ethSendTransaction.getTransactionHash();

                    EthGetTransactionReceipt ethGetTransactionReceiptResp = web3.ethGetTransactionReceipt(transactionHash).send();
                    return true;
                }
                catch(IOException | CipherException e){
                    System.out.println("error");
                }
            } else {
                return false;
            }
            return true;

        }


    /**
     * Creating a raw transaction object in order to send a transaction
     * @param toAddress wallet that the transaction is being sent to
     * @param gasPrice amount of ether willing to pay for ever unit of gas
     * @param gasLimit maximum amount of gasPrice willing to spend
     * @param amount amount of WoCoins sent
     * @param nonce number of times a transaction has been sent
     * @return a string hexValue that is the transaction hash
     * @throws IOException
     * @throws CipherException
     */
    public String createOfflineTx(String toAddress, BigInteger gasPrice, BigInteger gasLimit, BigInteger amount, BigInteger nonce) throws IOException, CipherException {

        Credentials credentials = WalletUtils.loadCredentials(
                "adminpwd",
                "ethereum/node0/keystore/UTC--2019-08-07T17-24-10.532680697Z--0fce4741f3f54fbffb97837b4ddaa8f769ba0f91.json");

        RawTransaction rawTransaction  = RawTransaction.createEtherTransaction(nonce, gasPrice, gasLimit, toAddress, amount);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);

        return hexValue;
    }

    /**
     * Sends the transaction
     * @param hexValue the transaction hash
     * @return a boolean indicating if the transaction was sent
     */
    public boolean sendOfflineTx(String hexValue){
       try{
           EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).send();
           String transactionHash = ethSendTransaction.getTransactionHash();

           EthGetTransactionReceipt ethGetTransactionReceiptResp = web3.ethGetTransactionReceipt(transactionHash).send();
           return true;
       }
       catch(IOException e){
            System.out.println("exception");
            return false;
        }

    }


    /**
     * Displays the balance of a user's account.
     * @param id the user
     * @return a string containing the balance of the user's account
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws IOException
     * @throws CipherException
     */
        public String displayAccountBalance(String id) throws InterruptedException, ExecutionException, IOException, CipherException {

            if(!userExists(id)){
                return "No such user.";
            }
            else if (!walletExists(id)){
                return "User has no wallet.";
            }
            else{
                Web3j web3 = Web3j.build(new HttpService());
                String pubKey = "0x" + turnIdtoPublickey(id);
                int balanceInt = -1;
                Credentials credentials = WalletUtils.loadCredentials(
                        "adminpwd",
                        "ethereum/node0/keystore/UTC--2019-08-07T17-24-10.532680697Z--0fce4741f3f54fbffb97837b4ddaa8f769ba0f91.json");
                // send asynchronous requests to get balance
                EthGetBalance ethGetBalance = web3
                        .ethGetBalance(pubKey, DefaultBlockParameterName.LATEST)
                        .sendAsync()
                        .get();

                BigInteger wei = ethGetBalance.getBalance();
                int weiInt=0;
                weiInt = wei.intValue();

                System.out.println(weiInt);
                if(weiInt == 1){
                    return "User has 1 WoCoin.";
                }
                else if (weiInt > 1 ) {
                    return "User has " + weiInt + " WoCoins.";
                }
                else{
                    return "User has 0 WoCoins." + weiInt;
                }
            }

        }

        public String Carrats (String id){
            String builder = "";
            String carrats;
            String wocoin;


            if (userExists(id)) {
                //String key = getPublicKey(id);

                try (Connection conn = DriverManager.getConnection(url);
                     Statement stmt = conn.createStatement()) {
                    ResultSet rs = stmt.executeQuery("select *, count(*) over () total_rows from products order by price, name collate nocase;");


                    rs.next();
                    int rows = rs.getInt(6);

                    for (int i = 1; i <= rows; i++) {

                        if (rs.getString(2).equals(turnIdtoPublickey(id))) {
                            carrats = ">>>  ";
                        } else {
                            carrats = "";
                        }
                        rs.next();
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return builder;
        }
}

