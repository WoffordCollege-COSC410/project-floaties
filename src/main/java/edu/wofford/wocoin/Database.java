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

public class Database{
    private String adminPwd = "adminpwd";
    public Database(String path) {


        Utilities.createNewDatabase(path);
    }



    public String getAdminPwd(){
        return adminPwd;
    }


    public boolean checkIsAdmin(String password){
        return false;
    }




}


// constructor and is admin

// boolean check if user is in database

//  boolean add user

// remove user



