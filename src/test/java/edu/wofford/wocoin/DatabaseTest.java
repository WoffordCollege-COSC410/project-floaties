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

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            String testQuery = "SELECT count(*) FROM messages;";
            ResultSet rs = stmt.executeQuery(testQuery);

            assertEquals(rs.getInt(1), 0);

            String queryUsers = "SELECT count(*) FROM users";
            ResultSet ru = stmt.executeQuery(queryUsers);

            assertEquals(ru.getInt(1), 0);

            String queryWallets = "SELECT count (*) from wallets";
            ResultSet rw = stmt.executeQuery(queryWallets);

            assertEquals(rw.getInt(1), 0);

            String queryProducts = "SELECT count (*) from products";
            ResultSet rp = stmt.executeQuery(queryProducts);

            assertEquals(rp.getInt(1), 0);


        }
        catch (SQLException e) {
            e.printStackTrace();
            assertTrue(e.toString(), false);
        }



    }

    @Test
    public void createExistingdb() {
        String fileName = "src/test/resources/test6789DB.db";
        File file = new File(fileName);
        assertTrue("the database file exists", file.exists());

        Database db = new Database(fileName);
        assertTrue("the database file exists after instantiation", file.exists());

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
        }
        catch (SQLException e) {
            e.printStackTrace();
            assertTrue(e.toString(), false);
        }

        try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement()) {
                String testQuery = "SELECT count(*) FROM messages;";
                ResultSet rs = stmt.executeQuery(testQuery);

                assertEquals(rs.getInt(1), 2);

                String queryUsers = "SELECT count(*) FROM users";
                ResultSet ru = stmt.executeQuery(queryUsers);

                assertEquals(ru.getInt(1), 4);

                String queryWallets = "SELECT count (*) from wallets";
                ResultSet rw = stmt.executeQuery(queryWallets);

                assertEquals(rw.getInt(1), 4);

                String queryProducts = "SELECT count (*) from products";
                ResultSet rp = stmt.executeQuery(queryProducts);

                assertEquals(rp.getInt(1), 7);


            }
        catch (SQLException e) {
            e.printStackTrace();
            assertTrue(e.toString(), false);
        }

    }


    @Test
    public void testOpenExistingdb(){
        //move db into test resources
        String fileName = "src/test/resources/test45DB.db";
        File file = new File(fileName);
        assertTrue(file.exists());

        //make the file in src/test/java/resources first

        Database db = new Database(fileName);

        String [] myArray = new String[1];
        myArray[0] = fileName;
        Feature00Main.main(myArray);

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

        assertTrue(db.addUser("kara", "kara"));

        try (Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            assertNotNull(rs.next());
            assertEquals("kara", rs.getString(1));
            assertEquals(db.salt, rs.getInt(2));

        } catch (SQLException e) {
          e.printStackTrace();
        }



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

    public void addUserDBthatExists(){
        String fileName = "src/test/resources/testAddToAFullDB.db";

        File file = new File(fileName);


        Database db = new Database(fileName);
        String url = "jdbc:sqlite:" + fileName;

        assertTrue(db.addUser("kara", "porter"));

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            assertNotNull(rs.next());
            assertEquals("kara", rs.getString(1));
            assertEquals(db.salt, rs.getInt(2));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Ignore
    @Test
    public void pathDoesNotExist() {
        String pathToDB = "notThePathToDB.db";
        Database DB = new Database(pathToDB);
        assertFalse(DB.doesExist(pathToDB));

        File file = new File(pathToDB);
        file.delete();
    }



}