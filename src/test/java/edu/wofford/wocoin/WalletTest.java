package edu.wofford.wocoin;
import cucumber.api.java.bs.I;
import edu.wofford.wocoin.main.*;

/*
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


public class WalletTest{


    @Test
    public void testAddWalletToExistingWalletOverwrite() throws IOException{

        String fileName = "src/test/resources/testdb.db";
        String destName = "src/test/resources/testdbcopy.db";
        File file = new File(fileName);
        File dest = new File(destName);
        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        Database db = new Database (destName);


        db.createWallet("jdoe", destName, "password");

        assertTrue(db.walletExists("jdoe"));


        dest.delete();

    }



    @Test
    public void testAddWalletToExistingWalletNotOverwrite() throws IOException{


        String fileName = "src/test/resources/testdb.db";
        String destName = "src/test/resources/testdbcopy.db";
        File file = new File(fileName);
        File dest = new File(destName);
        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        Database db = new Database (destName);


        db.createWallet("jdoe", destName, "password");

        assertTrue(db.walletExists("jdoe"));

        db.createWallet("jdoe", destName, "password");

        assertTrue(db.walletExists("jdoe"));

        dest.delete();
    }

    @Test
    public void testAddWalletToANonExistentUser() throws IOException{
        String fileName = "src/test/resources/testdb.db";
        String destName = "src/test/resources/testdbcopy.db";
        File file = new File(fileName);
        File dest = new File(destName);
        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        Database db = new Database (destName);


        db.createWallet("Mr DNE", destName, "DNE");

        assertTrue(!db.walletExists("Mr DNE"));
        dest.delete();
    }
}