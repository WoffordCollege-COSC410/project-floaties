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
import java.io.*;
import java.nio.file.*;
import java.sql.*;


public class TransactionTest{

    @Test
    public void sendFundsToUserDNETest() throws IOException {
        String fileName = "src/test/resources/testdb.db";
        String destName = "src/test/resources/testdbcopy.db";
        File file = new File(fileName);
        File dest = new File(destName);
        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        assertTrue(file.exists());
        Database db = new Database(destName);
        assertTrue(!db.sendTransaction("MR. DNE", 69));
        dest.delete();
    }
    @Test
    public void sendFundsToUserWalletDNETest() throws IOException {
        String fileName = "src/test/resources/testdb.db";
        String destName = "src/test/resources/testdbcopy.db";
        File file = new File(fileName);
        File dest = new File(destName);
        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        assertTrue(file.exists());
        Database db = new Database(destName);
        db.addUser("Snoop Dogg", "GinAndJuice");
        assertTrue(!db.sendTransaction("Snoop Dogg", 5));
        dest.delete();
    }
    @Test
    public void sendFundsToUserTest() throws IOException{
        String fileName = "src/test/resources/testdb.db";
        String destName = "src/test/resources/testdbcopy.db";
        File file = new File(fileName);
        File dest = new File(destName);
        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        assertTrue(file.exists());
        Database db = new Database(destName);
        assertTrue(db.sendTransaction("jsmith", 420));
        dest.delete();
    }



}