package edu.wofford.wocoin;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.io.*;
import java.nio.file.*;
import java.sql.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DatabaseTests {

    @Test
    public void isAdmin() {
        Database d = new Database("testDB");
        String pwd = "adminpwd";
        assertEquals(true, d.checkIsAdmin(pwd));
    }

    @Test
    public void isNotAdmin() {
        String pwd = "notThePwd";
        Database d = new Database("testDB");
        assertEquals(false, d.checkIsAdmin(pwd));
    }

    @Test
    public void testCreateNewDatabase() {
        File tempFile = new File("testDB");

        if(tempFile.exists()) {
            tempFile.delete();
        }

        Database d = new Database("testDB");
        String url = "jdbc:sqlite:" + "testDB";

        //loop through database and check each table


        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            String testQuery = "SELECT * FROM users;";
            ResultSet rs = stmt.executeQuery(testQuery);
            assertTrue(!rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testFullDatabase() {
        String url = "jdbc:sqlite:" + "fullDB";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            String testQuery = "SELECT * FROM users;";
            ResultSet rs = stmt.executeQuery(testQuery);
            assertTrue(rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDoesDBExist() {
        Database d = new Database("testDB");
        assertTrue(d.doesExist("testDB"));
    }

    @Test
    public void tesAddUser() {
        Database d = new Database("testDb");
        boolean user = d.addUser("kporter", "password1");
        assertTrue(user);

        String url = "jdbc:sqlite:" + "testDB";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            String testQuery = "select id from users where id = 'kporter';";
            ResultSet rs = stmt.executeQuery(testQuery);
            rs.last();
            assertEquals(1, rs.getRow());
            assertEquals("kporter", rs.getString("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
