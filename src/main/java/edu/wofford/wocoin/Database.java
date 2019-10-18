package edu.wofford.wocoin;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.Test;
import java.io.*;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;
import java.sql.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.DefaultBlockParameterName;

public class Database {
    private String adminPwd = "adminpwd";
    private String url;
    private Connection con;
    protected boolean detectsExisting;


    public Database(String fileName) {
        String workingDir = System.getProperty("user.dir");
        String fullPath = workingDir + "\\" + fileName;
        File file = new File(fullPath);

        if(! file.exists()){
            System.out.println("File does not exist! About to create new blank db");
            Utilities.createNewDatabase(fullPath);
            boolean detectsExisting = false;
        }else{
            System.out.println("File exists");
            detectsExisting = true;
        }

        url =  "jdbc:sqlite:" + fullPath;
        this.adminPwd="adminpwd";
    }


    public  String getAdminPwd(){
        return adminPwd;
    }


    public boolean checkIsAdmin(String password){
        if(password.equals(getAdminPwd())){
            return true;
        }
        else{
            return false;
        }


    }

    private boolean userExists(String username) {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()){


            String testQuery = String.format("SELECT id from users where id = %s", username);

            ResultSet rs = stmt.executeQuery(testQuery);

            if (rs.wasNull() == false) {
                return false;
            } else {
                return true;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return true;
        }
    }



    public boolean addUser(String username, String password) {
        if(!userExists(username)){
            String saltedPasswd = "";
            int salt = generateSalt();
            saltedPasswd = getSaltedPasswd(password, salt);
            String hash = getHash(saltedPasswd);
            //url is empty
            //sql statement addUser



            try (Connection conn= DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement()){

                String testQuery = String.format("INSERT INTO users (id, salt, hash) VALUES (%s, %d,%s);", username,salt,hash);
                stmt.executeUpdate(testQuery);
                return true;
            }
            catch(SQLException e){
                e.printStackTrace();
                System.out.println("EXCEPTION.");
                return false;
            }
        }
        else{
            return false;
        }

    }


    private int generateSalt(){
        int saltValue = Utilities.generateSalt();
        return saltValue;
    }


    private String getSaltedPasswd(String passWd, int saltValue){

        String SaltValueString = Integer.toString(saltValue);
        String builder = passWd + SaltValueString;
        return builder;

    }
    private String getHash(String saltedPasswd){
        String hash = "";
        hash = Utilities.applySha256(saltedPasswd);

        return hash;
    }


}


// constructor and is admin

// boolean check if user is in database

//  boolean add user

// remove user



