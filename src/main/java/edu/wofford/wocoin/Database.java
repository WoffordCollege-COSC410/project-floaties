package edu.wofford.wocoin;

import org.junit.Test;
import java.io.*;
import java.sql.*;



//package edu.wofford.wocoin;
//import java.sql.*;
//import java.io.EOFException;
//import java.io.IOException;
//import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.Cipher;
import org.apache.commons.io.FileUtils;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.lang.Exception.*;
//import oracle.security.crypto.core.*;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.CipherException;
//import org.apache.beam.sdk.coders.CoderException;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.crypto.WalletUtils;
import org.web3j.crypto.*;
import java.security.NoSuchAlgorithmException;
import java.security.*;
import java.lang.Exception.*;


public class Database {
    private String adminPwd;
    private String url;
    private Connection con;
    private Web3j web3;

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
     * returns the administrators password
     */

    public  String getAdminPwd(){
        return adminPwd;
    }

    /**
     * returns TRUE if the caller's input is administrator
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
     *helper method to add user currently not working
     * @return boolean if the user exists
     */

    public boolean userExists(String id) {


       // String testQuery = "SELECT id FROM users WHERE id = ?;";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement("SELECT id FROM users WHERE id = ?;")){

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
     *currently not working depends on the above UserExists method that is not correctly working
     * we know this because of the code coverage report that does not go inside the main if of the function
     * @param id this will be the value stored in the id column of the Database
     * @param password this value will be salted and hashed and stored in the salt and hash columns of the DB
     * @return boolean if user exists or not
     */

    public boolean addUser(String id, String password) {
        if(!userExists(id)){
            String saltedPasswd;
            int salt = generateSalt();
            saltedPasswd = getSaltedPasswd(password, salt);
            String hash = getHash(saltedPasswd);

            String testQuery = "INSERT INTO users (id, salt, hash) VALUES (?, ?, ?);";

            try (Connection conn= DriverManager.getConnection(url);
                 PreparedStatement stmt = conn.prepareStatement(testQuery)){

                stmt.setString(1, id);
                stmt.setInt(2, salt);
                stmt.setString(3, hash);
                stmt.executeUpdate();


                return true;
            }
            catch(SQLException e){
                e.printStackTrace();
                return false;
            }
        }
        else{
            return false;
        }

    }
    /**
     * Removes a user identified by their id from the Database
     * @param id a value passed in by the user to be removed
     * @return a boolean value of if the user was removed or not
     */
    public boolean removeUser(String id){
        if(userExists(id)){
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
    private String getHash(String saltedPasswd){
        return Utilities.applySha256(saltedPasswd);
    }






    private boolean isAValidUser(String id){
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement("SELECT id FROM users WHERE id = ?;")){

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



    private boolean DBQueryWalletID (String id){


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


    public boolean walletExists(String id ){
        File existingWalletFile = new File("tmp//" + id + "//");

        if (existingWalletFile.isDirectory() ){//&& DBQueryWalletID(id)) {
            return true;
        }
        else{
            return false;
        }

    }


    public boolean CreateWallet(String id) {

        if (!isAValidUser(id)) {
            return false;
        }
        else{
            if (walletExists(id)) {
                try {
                    String directoryString = "tmp//" + id + "//";
                    File directory = new File(directoryString);
                    FileUtils.cleanDirectory(directory);
                    directory.delete();

                    String destinationDir = "tmp//" + id + "//";
                    File destination = new File(destinationDir);
                    destination.mkdirs();

                    web3 = Web3j.build(new HttpService("https://mainnet.infura.io/v3/338a115fa5324abeadccd992f9c6cbab"));

                    String walletFileName = WalletUtils.generateFullNewWalletFile("password", destination);
                    //Credentials credentials = WalletUtils.loadCredentials("password", "/ethereum/node0/keystore/UTC--2019-08-07T17-24-10.532680697Z--0fce4741f3f54fbffb97837b4ddaa8f769ba0f91.json");
                    //File f2 = new File(destinationDir + walletFileName);

                    String[] fetchAddress = walletFileName.split("--");

                    String getAddress = fetchAddress[fetchAddress.length - 1].split("\\.")[0];


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
                        stmt.setString(2, getAddress);
                        stmt.executeUpdate();
                        return true;


                    } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                    }
                } catch (NoSuchAlgorithmException e) {
                    System.out.println("exception");
                    e.printStackTrace();
                    return false;
                } catch (SecurityException e) {
                    e.printStackTrace();
                    return false;
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                    return false;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                } catch (CipherException e) {
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

                    return true;
                } catch (NoSuchAlgorithmException e) {
                    System.out.println("exception");
                    e.printStackTrace();
                    return false;
                } catch (SecurityException e) {
                    e.printStackTrace();
                    return false;
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                    return false;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                } catch (CipherException e) {
                    e.printStackTrace();
                    return false;
                }
            }


        }


    }

    private String turnPublicKeyToId(String publicKey){
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement("SELECT id FROM wallets WHERE publickey = ?;")){

            stmt.setString(1,publicKey);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                if(rs.getString(1).equals(publicKey)){
                    return rs.getString(1);
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

    public boolean addProduct(String seller, int price, String name, String description){
        String id = turnPublicKeyToId(seller);

        if(walletExists(id)){ //change stubs to real methods from parent classes
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






}
