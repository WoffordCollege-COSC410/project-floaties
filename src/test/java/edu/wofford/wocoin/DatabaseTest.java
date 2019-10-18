package edu.wofford.wocoin;

/**
 * Tests for class Databases.
 * <p>
 * All tests in the folder "test" are executed
 * when the "Test" action is invoked.
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

public class DatabaseTest {

    @Test
    public void testCreateNewDatabase() {
        String fileName = "testDB.db";
        System.out.println("path = " + fileName);
        System.out.println(System.getProperty("user.dir") + "\\" + fileName);
        String workingDir = System.getProperty("user.dir");
        String fullPath = workingDir + "\\" + fileName;
        File file = new File(fullPath);
        if (file.exists()) {
            file.delete();
        }

        Database db = new Database(fileName);
        assertTrue(file.exists());
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

    @Test
    public void testDetectsFullDatabase() {
        String fileName = "testDB.db";

        String workingDir = System.getProperty("user.dir");
        String fullPath = workingDir + "\\" + fileName;
        File file = new File(fullPath);
        assertTrue(!file.exists());
        Utilities.createTestDatabase(fullPath);

        assertTrue(file.exists());
        System.out.println("Test DB created and confirmed");

        Database db = new Database(fileName);
        assertTrue(db.detectsExisting);

        file.delete();

    }


    @Test
    public void testGetAdminPwd() {
        String path = "testDB.db";
        String Robert = "C://Users//RB//Desktop//COSC410//groupProject//";
        String Sergio = "C://Users//summu//Desktop//Wofford//Senior Year//Software Engineering//Project//";
        File file = new File(Sergio + "project-floaties//" + path);
        if (file.exists()) {
            file.delete();
        }
        Database test = new Database("test45DB");
        assertEquals("adminpwd", test.getAdminPwd());
        file.delete();
    }
    @Test
    public void testIsAdminPassWdCorrect() {
        String path = "test45DB.db";
        String Robert = "C://Users//RB//Desktop//COSC410//groupProject//";
        String Sergio = "C://Users//summu//Desktop//Wofford//Senior Year//Software Engineering//Project//";
        File file = new File(Sergio + "project-floaties//" + path);
        if (file.exists()) {
            file.delete();
        }
        Database test = new Database("test45DB.db");
        assertTrue(test.checkIsAdmin("adminpwd"));
        file.delete();
    }
    @Test
    public void testAddUser() {
        String path = "testDB.db";
        String Sergio = "C://Users//summu//Desktop//Wofford//Senior Year//Software Engineering//Project//";
        String Robert = "C://Users//RB//Desktop//COSC410//groupProject//";
        File file = new File(Sergio + "project-floaties//" + path);
        if (file.exists()){
            file.delete();
        }
        System.out.println("path = " + path);
        System.out.println("file = " + file);
        System.out.println(System.getProperty("user.dir"));
        assertTrue(false);
        Database db = new Database(path);
        db.addUser("kporter", "1234");
        String url = "jdbc:sqlite:" + path;
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            String testQuery = "select id from users where id = 'kporter';";
            ResultSet rs = stmt.executeQuery(testQuery);
            //rs.next();
            //rs.next();
            rs.last();
            //assertEquals(works, true);
            //assertEquals(2, rs.getRow());
            String usernameInDB = rs.getString(1);
            String username = "kportersddddd";
            assertEquals(username, usernameInDB);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


//    @Test
//    public void isAdmin(){
//