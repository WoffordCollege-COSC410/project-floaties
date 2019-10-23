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
import org.junit.Ignore;
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

public class DatabaseTest {
    /*
     tests ss
     */

    @Ignore
    @Test
    public void testCreateNewDatabase() {

        String fileName = "testDB.db";
        String workingDir = System.getProperty("user.dir");

        String fullPath = workingDir + "\\" + fileName;


        File file = new File(fullPath);
        if (file.exists()) {
            file.delete();
        }
        Database db = new Database(fileName);
        String url = "jdbc:sqlite:" + fileName;
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            String testQuery = "SELECT * FROM users;";
            ResultSet rs = stmt.executeQuery(testQuery);
            assertTrue(!rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            String testQuery = "SELECT * FROM wallets;";
            ResultSet rs = stmt.executeQuery(testQuery);
            assertTrue(!rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            String testQuery = "SELECT * FROM products;";
            ResultSet rs = stmt.executeQuery(testQuery);
            assertTrue(!rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            String testQuery = "SELECT * FROM messages;";
            ResultSet rs = stmt.executeQuery(testQuery);
            assertTrue(!rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //this is checking number of tables
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            String testQuery = "SELECT count(*) FROM sqlite_master WHERE type = 'table' AND name != 'android_metadata' AND name != 'sqlite_sequence';";
            ResultSet rs = stmt.executeQuery(testQuery);
            assertEquals(rs.getInt(1), 4);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        file.delete();

    }

    @Ignore
    @Test
    public void testDuplicateDatabase() {

        String fileName = "testDB.db";
        String workingDir = System.getProperty("user.dir");

        String fullPath = workingDir + "\\" + fileName;


        File file = new File(fullPath);
        if (file.exists()) {
            file.delete();
        }
        Database db = new Database(fileName);
        String url = "jdbc:sqlite:" + fileName;


        try {
            Connection con = DriverManager.getConnection(url);
            if (con != null) {

                assertTrue(con != null);
                //first db is made
            } else {
                assertTrue(con != null); //make sure it goes in if != is wrong it is supposed to be ==
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }

        Database db2 = new Database(fileName);
        try {
            Connection con = DriverManager.getConnection(url);
            if (con != null) {

                assertTrue(con != null);
                //db 2 is made

            } else {
                assertTrue(con != null);//bad conditional forcing it into if
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        file.delete();

        // assertEquals(db, null);
        //ssertEquals(db2,  )
    }

    @Ignore
    @Test
    public void testDetectsFullDatabase() {
        String fileName = "testDBFULL.db";


        String workingDir = System.getProperty("user.dir");
        String fullPath = workingDir + "\\" + fileName;
        File file = new File(fullPath);
        assertTrue(!file.exists());
        Utilities.createTestDatabase(fullPath);
        assertTrue(file.exists());


        Database db = new Database(fileName);
        assertTrue(db.detectsExisting);

        file.delete();


    }

    @Test
    public void testGetAdminPwd() {
        Database test = new Database("test45DB.db");
        assertEquals("adminpwd", test.getAdminPwd());
    }

    @Test
    public void testIsAdminPassWdCorrect() {
        Database test = new Database("test45DB.db");
        assertTrue(test.checkIsAdmin("adminpwd"));
    }

    @Ignore
    @Test
    public void testAddUser() {
        //DriverManager.loadInitialDrivers();
        String fileName = "testDB.db";
        String workingDir = System.getProperty("user.dir");

        String fullPath = workingDir + "\\" + fileName;
        File file = new File(fullPath);
        if (file.exists()) {
            file.delete();
        }

        Database db = new Database(fileName);
        String url = "jdbc:sqlite:" + fileName;

        //try (Connection conn = DriverManager.getConnection(url);
        //    Statement stmt = conn.createStatement()) {
        // String testQuery = "SELECT * FROM users;";
        //ResultSet rs = stmt.executeQuery(testQuery);
        //assertTrue(!rs.next());
        //} catch (SQLException e) {
        //  e.printStackTrace();
        //}

        //make sure db

        boolean first;
        first = db.addUser("kporter", "password");
        boolean second;
        second = db.addUser("kporter", "password");

        assertTrue(first);
        assertTrue(second);


        //String url = "jdbc:sqlite:" + fileName;
        //try (Connection conn= DriverManager.getConnection(url);
        // Statement stmt = conn.createStatement()){
        //String testQuery = "SELECT * FROM users;";
        // ResultSet rs = stmt.executeQuery(testQuery);
        // assertTrue( !rs.next());
        //  }
        //catch(SQLException e) {
        //e.printStackTrace();

        // }

        //  file.delete();

    }

    @Ignore
    @Test
    public void testAddDuplicateUser() {

    }


    @Test
    public void pathDoesExist() {
        String pathToDB = "pathToDB.db";
        Database DB = new Database(pathToDB);
        assertTrue(DB.doesExist(pathToDB));

        File file = new File(pathToDB);
        file.delete();
    }

    @Test
    public void pathDoesNotExist() {
        String pathToDB = "notThePathToDB.db";
        Database DB = new Database(pathToDB);
        assertFalse(DB.doesExist(pathToDB));

        File file = new File(pathToDB);
        file.delete();
    }
}