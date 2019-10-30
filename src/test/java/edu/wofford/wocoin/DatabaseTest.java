package edu.wofford.wocoin;
import edu.wofford.wocoin.main.*;

/**
 * Tests for class Databases.
 *
 * All tests in the folder "test" are executed
 * when the "Test" action is invoked.
 *
 */

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.*;
import java.sql.*;


public class DatabaseTest {
    /*
     tests ss
     */

    @Test
    public void newDbIfPathDNE() {
        String path = "iDontExist.db";
        //test creating database without double checking
    }

    @Test
    public void testCreateNewDatabase() {

        String fileName = "src/test/resources/testblankDB.db";

        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        System.out.println("test full path = " + fileName);
        Database db = new Database(fileName);
        String url = "jdbc:sqlite:" + fileName;


        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_master WHERE type = 'table' AND name != 'android_metadata' AND name != 'sqlite_sequence';");
            assertNotNull(rs.next());
            assertEquals("users", rs.getString(2));
            assertNotNull(rs.next());
            assertEquals("wallets", rs.getString(2));
            assertNotNull(rs.next());
            assertEquals("products", rs.getString(2));
            assertNotNull(rs.next());
            assertEquals("messages", rs.getString(2));

        } catch (SQLException e) {
            e.printStackTrace();
            assertTrue(e.toString(), false);
        }

        //file.delete();


    }


    @Test
    public void testOpenExistingdb() {
        //move db into test resources
        String fileName = "src/test/resources/test45DB.db";
        File file = new File(fileName);
        assertTrue(file.exists());

        //make the file in src/test/java/resources first

        Database db = new Database(fileName);

        //instantiate db with the file with the whole path


        //Utilities.createTestDatabase(filename);
        assertTrue(file.exists());
        String url = "jdbc:sqlite:" + fileName;

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_master WHERE type = 'table' AND name != 'android_metadata' AND name != 'sqlite_sequence';");
            assertNotNull(rs.next());
            assertEquals("users", rs.getString(2));
            assertNotNull(rs.next());
            assertEquals("wallets", rs.getString(2));
            assertNotNull(rs.next());
            assertEquals("products", rs.getString(2));
            assertNotNull(rs.next());
            assertEquals("messages", rs.getString(2));

        } catch (SQLException e) {
            e.printStackTrace();
            assertTrue(e.toString(), false);
        }

        try (Connection conn= DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()){
            String testQuery = "SELECT count(*) FROM messages;";
            ResultSet rs = stmt.executeQuery(testQuery);

            assertEquals(rs.getInt(1),2);

            String queryUsers = "SELECT count(*) FROM users";
            ResultSet ru = stmt.executeQuery(queryUsers);

            assertEquals(ru.getInt(1), 4);

            String queryWallets = "SELECT count (*) from wallets";
            ResultSet rw = stmt.executeQuery(queryWallets);

            assertEquals(rw.getInt(1),4);

            String queryProducts = "SELECT count (*) from products";
            ResultSet rp = stmt.executeQuery(queryProducts);

            assertEquals(rp.getInt(1),7);



        }
        catch(SQLException e){
            e.printStackTrace();
        }





        //verify file exists
        //Instatntiate db with that file as a param
        // asserts from create new db instead of looking at tablenames


        //make sure we dont over write a new db
    }


    @Test
    public void testGetAdminPwd() {
        String fileName = "test1DB.db";
        File file = new File(fileName);
        Database test = new Database("testDB.db");
        assertEquals("adminpwd", test.getAdminPwd());
        file.delete();
    }

    @Test
    public void testIsAdminPassWdCorrect() {
        String fileName = "test2DB.db";
        File file = new File(fileName);
        Database test = new Database("testDB.db");
        assertTrue(test.checkIsAdmin("adminpwd"));
        file.delete();
    }


    @Test
    public void testAddUserEmptyDB() {

        //works 100% of the time
        //DriverManager.loadInitialDrivers();
        String fileName = "src/test/resources/testAddKaraUserDB.db";

        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }

        Database db = new Database(fileName);
        String url = "jdbc:sqlite:" + fileName;

        assertTrue(db.addUser("kara", "porter"));

        try (Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            assertNotNull(rs.next());
            assertEquals("kara", rs.getString(1));

            String saltedPwd = "porter" + rs.getInt(2);
            String hash = Utilities.applySha256(saltedPwd);
            assertEquals(hash, rs.getString(3));

        } catch (SQLException e) {
          e.printStackTrace();
        }

        //make sure db
        file.delete();


    }

    @Test
    public void addUserToExistingDB(){
        String fileName = "src/test/resources/testAddToAFullDB.db";

        File file = new File(fileName);

        Database db = new Database(fileName);
        String url = "jdbc:sqlite:" + fileName;

        assertTrue(db.addUser("robert333", "porter"));

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            assertNotNull(rs.next());
            assertEquals("robert333", rs.getString(1));

            String saltedPwd = "porter" + rs.getInt(2);
            String hash = Utilities.applySha256(saltedPwd);
            assertEquals(hash, rs.getString(3));

           // assertEquals(saltedPwd, rs.getString(2));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}