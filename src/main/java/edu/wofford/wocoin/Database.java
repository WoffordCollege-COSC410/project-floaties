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

        if(doesExist(path)){
            this.adminPwd="adminpwd";
        }
        else{
            Utilities.createNewDatabase(path);
        }
        url =  "jdbc:sqlite:" + path;
        con = null;
        

    }

    public boolean doesExist(String path) {
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



    public String getAdminPwd(){
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




>>>>>>> b344301eb6f06672b589aefa3600f5373bfa0cc2
}


// constructor and is admin

// boolean check if user is in database

//  boolean add user

// remove user



