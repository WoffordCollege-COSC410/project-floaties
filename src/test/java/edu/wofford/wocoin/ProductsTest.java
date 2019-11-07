package edu.wofford.wocoin;


import static org.junit.Assert.*;

import cucumber.api.java.bs.I;
import org.junit.Ignore;
import org.junit.Test;
import java.io.*;
import java.nio.file.*;
import java.sql.*;


public class ProductsTest {

    @Test
    public void testProductNameValid() throws IOException{
        String fileName = "src/test/resources/testdb.db";
        String destName = "src/test/resources/testdbcopy.db";
        File file = new File(fileName);
        File dest = new File(destName);
        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

        System.out.println("test full path = " + destName);

        String user = "jsmith";
        Products p = new Products(fileName, user);
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
        Products p = new Products(fileName, user);
        assertTrue(! p.addProduct("walletKey", 5, "jsmith",""));

        dest.delete();
    }

    @Test
    public void testProductPriceValid() throws IOException{
        String fileName = "src/test/resources/testdb.db";
        String destName = "src/test/resources/testdbcopy.db";
        File file = new File(fileName);
        File dest = new File(destName);
        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

        System.out.println("test full path = " + destName);

        String user = "jsmith";
        Products p = new Products(fileName, user);
        assertTrue(! p.addProduct("walletKey", 0, "name","description"));

        dest.delete();
    }

    @Test
    public void testAddProduct() throws IOException {
        String fileName = "src/test/resources/testdb.db";
        String destName = "src/test/resources/testdbcopy.db";
        File file = new File(fileName);
        File dest = new File(destName);
        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

        Products p = new Products(destName, "jsmith");
        String url = "jdbc:sqlite:" + destName;

        assertTrue(p.addProduct("walletkey", 3, "lamp", "light"));

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM products WHERE name = 'lamp';");
            assertEquals("lamp", rs.getString(4));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        dest.delete();

    }

}
