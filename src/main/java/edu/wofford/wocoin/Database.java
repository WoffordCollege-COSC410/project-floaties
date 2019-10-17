package edu.wofford.wocoin;
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


    public Database(String path) {

        if(doesExist()){
            this.adminPwd="adminpwd";
            //come back and delete the file and make a new db like in our test???
            url =  "jdbc:sqlite:" + path;


        }
        else{
            Utilities.createNewDatabase(path);
            url =  "jdbc:sqlite:" + path;
        }


        

    }

    public Database(String path, int full){



        if(full == 1) {
            if (doesExist()) {
                this.adminPwd = "adminpwd";
            } else {
                Utilities.createTestDatabase(path);
            }

            url = "jdbc:sqlite:" + path;
            con = null;
        }
        else {
            this.adminPwd = "adminpwd";
        }
    }


    private boolean doesExist() {
         con = null;
        ResultSet rs = null;


        try {
            con = DriverManager.getConnection(url);
            if (con != null) {
                rs = con.getMetaData().getCatalogs();
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
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
        return true;
    }



}


// constructor and is admin

// boolean check if user is in database

//  boolean add user

// remove user



