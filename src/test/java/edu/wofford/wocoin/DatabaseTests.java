package edu.wofford.wocoin;

/**
 * Tests for class Databases.
 *
 * All tests in the folder "test" are executed
 * when the "Test" action is invoked.
 *
 */

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.Test;

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

public class DatabaseTests{
    Database test = new Database("testDB");

    @Test
    public void testCreateNewDatabase(){

        String url = "jdbc:sqlite:" + "testDB";
        try(Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()) {
            String testQuery = "SELECT * FROM users;";
            ResultSet rs = stmt.executeQuery(testQuery);
            assertTrue( !rs.next() );
        }catch(SQLException e){
            e.printStackTrace();
        }

    }
    public void testAddToUsers(){

    }

    public void testDoesDBExist(String path){

    }


    @Test
    public void testGetAdminPwd(){
        assertEquals("adminpwd", test.getAdminPwd());
    }

//    @Test
//    public void isAdmin(){
//
//    }
}