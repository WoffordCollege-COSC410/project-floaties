package edu.wofford.wocoin;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductTest {

    @Test
    public void testDisplayProducts() throws IOException {
        String fileName = "src/test/resources/testdb.db";
        String destName = "src/test/resources/testdbcopy.db";
        File file = new File(fileName);
        File dest = new File(destName);
        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        Database db = new Database (destName);
        String url = "jdbc:sqlite:" + destName;

        try(Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from products where name = 'trip to Charlotte';");
            assertEquals(rs.last(), db.displayProductF6().indexOf(6));
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
