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
            Utilities.createNewDatabase(fileName);
            boolean detectsExisting = false;
        }else{
            System.out.println("File exists");
            detectsExisting = true;
        }

        url =  "jdbc:sqlite:" + fileName;
        this.adminPwd="adminpwd";
    }


    public  String getAdminPwd(){
        return adminPwd;
    }


    public boolean checkIsAdmin(String password){
        if(password == getAdminPwd()){
            return true;
        }
        else{
            return false;
        }


    }

    public boolean addUser(String username, String password) {
        String saltedPasswd = "";
        int salt = generateSalt();
        saltedPasswd = getSaltedPasswd(password, salt);
        String hash = getHash(saltedPasswd);

        //sql statement addUser
        try (Connection conn= DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()){

            String testQuery = String.format("INSERT INTO users (id, salt, hash) VALUES (%s, %d,%s)", username,salt,hash);
            ResultSet rs = stmt.executeQuery(testQuery);
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
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