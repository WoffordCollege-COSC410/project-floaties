package edu.wofford.wocoin;
import edu.wofford.wocoin.main.*;

/**
 * Tests for class Databases.
 *
 * All tests in the folder "test" are executed
 * when the "Test" action is invoked.
 *
 */
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.io.*;
import java.nio.file.*;
import java.sql.*;


public class DatabaseTest {

    @Test
    public void isAnAdminWrongTest() throws IOException {
        String fileName = "src/test/resources/testdb.db";
        String destName = "src/test/resources/testdbcopy.db";
        File file = new File(fileName);
        File dest = new File(destName);
        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        assertTrue(file.exists());
        Database db = new Database(destName);
       assertTrue(!db.checkIsAdmin("wrong"));
       dest.delete();
    }

    @Test
    public void isAnAdminRightTest() throws IOException{
        String fileName = "src/test/resources/testdb.db";
        String destName = "src/test/resources/testdbcopy.db";
        File file = new File(fileName);
        File dest = new File(destName);
        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        assertTrue(file.exists());
        Database db = new Database(destName);
        assertTrue(db.checkIsAdmin("adminpwd"));
        dest.delete();
    }

    @Test
    public void testCreateNewDatabase() throws IOException{

        String fileName = "src/test/resources/testdb.db";
        String destName = "src/test/resources/testdbcopy.db";
        File file = new File(fileName);
        File dest = new File(destName);
        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

        System.out.println("test full path = " + destName);
        Database db = new Database(destName);
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

        dest.delete();


    }


    @Test
    public void testOpenExistingdb() throws IOException{
        //move db into test resources
        String fileName = "src/test/resources/testdb.db";
        String destName = "src/test/resources/testdbcopy.db";
        File file = new File(fileName);
        File dest = new File(destName);
        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

        Database db = new Database(destName);
        String url = "jdbc:sqlite:" + destName;

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


        } catch (SQLException e) {
            e.printStackTrace();
        }
        dest.delete();


    }


    @Test
    public void testGetAdminPwd() throws IOException{
        String fileName = "src/test/resources/testdb.db";
        String destName = "src/test/resources/testdbcopy.db";
        File file = new File(fileName);
        File dest = new File(destName);
        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

        Database test = new Database(destName);
        assertEquals("adminpwd", test.getAdminPwd());
        dest.delete();
    }

    @Test
    public void testIsAdminPassWdCorrect() throws IOException {
        String fileName = "src/test/resources/testdb.db";
        String destName = "src/test/resources/testdbcopy.db";
        File file = new File(fileName);
        File dest = new File(destName);
        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

        Database test = new Database(destName);
        assertTrue(test.checkIsAdmin("adminpwd"));
        dest.delete();
    }


    @Test
    public void addUserToExistingDB() throws IOException {
        String fileName = "src/test/resources/testdb.db";
        String destName = "src/test/resources/testdbcopy.db";
        File file = new File(fileName);
        File dest = new File(destName);
        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

        Database db = new Database(destName);
        String url = "jdbc:sqlite:" + destName;

        assertTrue(db.addUser("kara", "porter"));

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT id FROM users WHERE id = 'kara';");

            if (rs.next()) {
                assertEquals("kara", rs.getString(1));
            } else {
                assertEquals(0, 1); //obviously false
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        dest.delete();

    }

    @Test
    public void testAddExistingUser() throws IOException {
        String fileName = "src/test/resources/testdb.db";
        String destName = "src/test/resources/testdbcopy.db";
        File file = new File(fileName);
        File dest = new File(destName);
        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

        Database db = new Database(destName);
        String url = "jdbc:sqlite:" + destName;
        String id = "srogers";

        assertFalse(db.addUser("jsmith", "porter"));

//        try (Connection conn = DriverManager.getConnection(url);
//             Statement stmt = conn.createStatement()) {
//            ResultSet rs = stmt.executeQuery("SELECT id FROM users WHERE id = 'jsmith';");
//
//        } catch(SQLException e) {
//            e.printStackTrace();
//        }

        dest.delete();
    }

    //@Ignore
    @Test
    public void testRemoveUser() throws IOException {
        String fileName = "src/test/resources/testdb.db";
        String destName = "src/test/resources/testdbcopy.db";
        File file = new File(fileName);
        File dest = new File(destName);
        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

        Database db = new Database(destName);
        String url = "jdbc:sqlite:" + destName;

        db.addUser("srogers", "porter");
        assertTrue(db.removeUser("srogers"));

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT id FROM users WHERE id = 'srogers';");
            assertFalse(rs.next());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        dest.delete();
    }

    @Test
    public void testRemoveUserThatDoesntExist() throws IOException {
        String fileName = "src/test/resources/testdb.db";
        String destName = "src/test/resources/testdbcopy.db";
        File file = new File(fileName);
        File dest = new File(destName);
        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

        String [] myArray = new String[1];
        myArray[0] = fileName;
        Feature00Main.main(myArray);

        //Utilities.createTestDatabase(filename);
        assertTrue(file.exists());
        String url = "jdbc:sqlite:" + fileName;

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT id FROM users WHERE id = 'kara';");
            assertFalse(rs.next());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        dest.delete();
        }


        // right here is wallet tests
        @Test
        public void testMakingWalletWhereUserDNE() throws IOException{

            String fileName = "src/test/resources/testdb.db";
            String destName = "src/test/resources/testdbcopy.db";
            File file = new File(fileName);
            File dest = new File(destName);
            Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Database db = new Database (destName);


            db.createWallet("MR DNE");

            assertTrue(!db.walletExists("MR DNE"));


            dest.delete();

        }

    @Test
    public void testMakingWalletWhereWalletDoesNotExistForThatUser() throws IOException{

        String fileName = "src/test/resources/testdb.db";
        String destName = "src/test/resources/testdbcopy.db";
        File file = new File(fileName);
        File dest = new File(destName);
        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        Database db = new Database (destName);

        db.addUser("kara", "1234");
        db.createWallet("kara");

        assertTrue(db.walletExists("kara"));


        dest.delete();

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

    @Test
    public void testturnIdtoPublickey() throws IOException{
        String fileName = "src/test/resources/testdb.db";
        String destName = "src/test/resources/testdbcopy.db";
        File file = new File(fileName);
        File dest = new File(destName);
        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

        String workingDir = System.getProperty("user.dir");
        String fullPath = workingDir + "\\" + fileName;
        Database db = new Database(destName);

        Utilities.createTestDatabase(fullPath);
        assertTrue(file.exists());

        assertEquals(db.turnIdtoPublickey("jsmith"), "a615316333ba8622fd5bb60fe39758b3515f774d");




        dest.delete();
    }

    @Test
    public void testturnIdtoPublickey2() throws IOException{
        String fileName = "src/test/resources/testdb.db";
        String destName = "src/test/resources/testdbcopy.db";
        File file = new File(fileName);
        File dest = new File(destName);
        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

        System.out.println("test full path = " + destName);

        String user = "jsmith";
        Database db = new Database(destName);

        assertEquals(db.turnIdtoPublickey("MR DNE"), "");




        dest.delete();
    }



        @Test
        public void testProductNameValid() throws IOException{
            String fileName = "src/test/resources/testdb.db";
            String destName = "src/test/resources/testdbcopy.db";
            File file = new File(fileName);
            File dest = new File(destName);
            Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

            System.out.println("test full path = " + destName);

            String user = "jsmith";
            Database p = new Database(destName);
            assertTrue(! p.addProduct("walletKey", 5, "","description"));

            dest.delete();

        }

        @Test
        public void testProductDescriptionValid () throws IOException{
            String fileName = "src/test/resources/testdb.db";
            String destName = "src/test/resources/testdbcopy.db";
            File file = new File(fileName);
            File dest = new File(destName);
            Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

            System.out.println("test full path = " + destName);

            String user = "jsmith";
            Database p = new Database(destName);
            assertTrue(! p.addProduct("walletKey", 5, "jsmith",""));

            dest.delete();
        }

        @Test
        public void testProductPriceInValid() throws IOException{
            String fileName = "src/test/resources/testdb.db";
            String destName = "src/test/resources/testdbcopy.db";
            File file = new File(fileName);
            File dest = new File(destName);
            Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

            Database db = new Database(destName);

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

            String user = "jsmith";
            Database p = new Database(destName);
            assertTrue(!p.addProduct("a615316333ba8622fd5bb60fe39758b3515f774d", 0, "lamp","FIAT LUX!"));

            dest.delete();
        }

        @Test
        public void testAddProduct() throws IOException {
            String fileName = "src/test/resources/testdb.db";
            String destName = "src/test/resources/testdbcopy.db";
            File file = new File(fileName);
            File dest = new File(destName);
            Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

            Database p = new Database(destName);
            String url = "jdbc:sqlite:" + destName;

            assertTrue(p.addProduct("a615316333ba8622fd5bb60fe39758b3515f774d", 3, "lamp", "light"));

            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM products WHERE name = 'lamp';");
                assertEquals("lamp", rs.getString(4));

            } catch (SQLException e) {
                e.printStackTrace();
            }

            dest.delete();

        }

        @Test
        public void testDisplayProductF6() throws IOException {
            String fileName = "src/test/resources/testdb.db";
            String destName = "src/test/resources/testdbcopy.db";
            File file = new File(fileName);
            File dest = new File(destName);
            Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

            Database p = new Database(destName);
            String url = "jdbc:sqlite:" + destName;

            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM products;");
                assertEquals(rs, p.displayProductF6());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            dest.delete();
        }

/*
        @Test
        public void nonExistentUserTest() throws IOException{

            String fileName = "src/test/resources/testdb.db";
            String destName = "src/test/resources/testdbcopy.db";
            File file = new File(fileName);
            File dest = new File(destName);
            Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

            Database db = new Database(destName);

            assertEquals(db.displayProduct("MR DNE"), "No such user.");

            dest.delete();

        }

        @Test
        public void showNoAddedProductsTest() throws IOException{
            String fileName = "src/test/resources/testdb.db";
            String destName = "src/test/resources/testdbcopy.db";
            File file = new File(fileName);
            File dest = new File(destName);
            Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

            Database db = new Database(destName);

            assertEquals(db.displayProduct("hjones")," false");

            dest.delete();
        }

        @Test
        public void testRemoveProduct() throws IOException{
            String fileName = "src/test/resources/testdb.db";
            String destName = "src/test/resources/testdbcopy.db";
            File file = new File(fileName);
            File dest = new File(destName);
            Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

            Database db = new Database(destName);

            assertTrue(db.removeProduct("jsmith", "chalk"));

            dest.delete();
        }

 */

}










