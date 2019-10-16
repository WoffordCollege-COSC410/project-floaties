package edu.wofford.wocoin;

<<<<<<< HEAD
import org.junit.Test;
import java.io.*;
import java.nio.file.*;

import static org.testng.Assert.assertEquals;

public class DatabaseTests {

    @Test
    public void databaseInstantiated() {

    }

    @Test
    public void isAdmin() {
        Database d = new Database();
        String pwd = "adminPwd";
        assertEquals(true, d.checkIsAdmin(pwd));
    }

    @Test
    public void isNotAdmin() {
        String pwd = "notThePwd";
        Database d = new Database();
        assertEquals(false, d.checkIsAdmin(pwd));
    }

    @Test
    public void testCreateNewDatabase() {

        String url = "jdbc:sqlite:" + "testDB";
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
        assertTrue(test.doesExist("testDB"));
    }
}
