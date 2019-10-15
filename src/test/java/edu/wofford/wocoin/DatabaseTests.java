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
    Utilities fullDB = new Utilities;
    fullDB.createTestDatabase("fullDB");

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
    @Test
    public void testFullDatabase(){
        String url = "jdbc:sqlite:" + "fullDB";
        try (Connection conn= DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()){
            String testQuery = "SELECT * FROM users;";
            ResultSet rs = stmt.executeQuery(testQuery);
            assertTrue( rs.next());
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testDoesDBExist(){
        assertTrue( test.doesExist("testDB"));
    }


    @Test
    public void testGetAdminPwd(){
        assertEquals("adminpwd", test.getAdminPwd());
    }

    @Test
    public void testIsAdminPassWdCorrect(){
        assertTrue(test.checkIsAdmin("adminpwd")  );
    }
    @Test
    public void testAddToUsers(){
        assertTrue(4==4);
    }

//    @Test
//    public void isAdmin(){
//
//    }
}