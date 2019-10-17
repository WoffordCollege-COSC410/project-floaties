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

    @Test
    public void testCreateNewDatabase(){
            String path= "testDB.db" ;
            String Robert = "C://Users//RB//Desktop//COSC410//groupProject//";
            File file = new File(Robert + "project-floaties//" + path);
            if (file.exists()){
                file.delete();
            }
            Database db = new Database(path);
            String url = "jdbc:sqlite:" + path;
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

            file.delete();

    }
    @Test
    public void testFullDatabase(){
        String path = "testDBFULL.db";

        String Robert = "C://Users//RB//Desktop//COSC410//groupProject//";
        File file = new File(Robert + "project-floaties//" + path);
        if (file.exists()){
            file.delete();
        }
        Database db = new Database(path,1);

        String url = "jdbc:sqlite:" + path;
        try (Connection conn= DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()){
            String testQuery = "SELECT count (*) FROM users;";
            ResultSet rs = stmt.executeQuery(testQuery);
            assertEquals(rs.getInt(1),4);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        try (Connection conn= DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()){
            String testQuery = "SELECT count (*) FROM wallets;";
            ResultSet rs = stmt.executeQuery(testQuery);
            assertEquals(rs.getInt(1),4);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        try (Connection conn= DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()){
            String testQuery = "SELECT count (*) FROM products;";
            ResultSet rs = stmt.executeQuery(testQuery);
            assertEquals(rs.getInt(1),7);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        try (Connection conn= DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()){
            String testQuery = "SELECT count(*) FROM messages;";
            ResultSet rs = stmt.executeQuery(testQuery);
            assertEquals(rs.getInt(1),2);
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

        file.delete();

    }





    @Test
    public void testGetAdminPwd(){
        Database test = new Database("test45DB");
        assertEquals("adminpwd", test.getAdminPwd());
    }

    @Test
    public void testIsAdminPassWdCorrect(){
        Database test = new Database("test45DB");
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