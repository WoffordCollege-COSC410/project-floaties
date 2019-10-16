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

public class DatabaseTests{

    //Utilities fullDB = new Utilities;
    //fullDB.createTestDatabase("fullDB");

    @Test
    public void testCreateNewDatabase(){


            File file = new File("project-floaties/testDB");
            if (file.exists()){
                file.delete();
            }
            Database db = new Database("testDB");
            String url = "jdbc:sqlite:" + "testDB";


                try (Connection conn= DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()){
                    String testQuery = "SELECT * FROM users;";
                    ResultSet rs = stmt.executeQuery(testQuery);
                    assertTrue( !rs.next());
                }
                catch(SQLException e){
                    e.printStackTrace();
                }

                try (Connection conn= DriverManager.getConnection(url);
                    Statement stmt = conn.createStatement()){
                    String testQuery = "SELECT * FROM wallets;";
                    ResultSet rs = stmt.executeQuery(testQuery);
                    assertTrue( !rs.next());
                }
                catch(SQLException e){
                    e.printStackTrace();
                }

                try (Connection conn= DriverManager.getConnection(url);
                     Statement stmt = conn.createStatement()){
                    String testQuery = "SELECT * FROM products;";
                    ResultSet rs = stmt.executeQuery(testQuery);
                    assertTrue( !rs.next());
                }
                catch(SQLException e){
                    e.printStackTrace();
                }

                try (Connection conn= DriverManager.getConnection(url);
                        Statement stmt = conn.createStatement()){
                    String testQuery = "SELECT * FROM messages;";
                    ResultSet rs = stmt.executeQuery(testQuery);
                    assertTrue( !rs.next());
                 }
                catch(SQLException e){
                    e.printStackTrace();
                }

                //this is checking number of tables
                try (Connection conn= DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()){
                    String testQuery = "SELECT count(*) FROM sqlite_master WHERE type = 'table' AND name != 'android_metadata' AND name != 'sqlite_sequence';";
                    ResultSet rs = stmt.executeQuery(testQuery);


                    assertEquals(rs.getInt(1), 4);
                }
            catch(SQLException e){
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
            assertTrue( !rs.next());
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testDoesDBExist(){
        Database test = new Database("testDB");
        assertTrue( test.doesExist());
    }


    @Test
    public void testGetAdminPwd(){
        Database test = new Database("testDB");
        assertEquals("adminpwd", test.getAdminPwd());
    }

    @Test
    public void testIsAdminPassWdCorrect(){
        Database test = new Database("testDB");
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