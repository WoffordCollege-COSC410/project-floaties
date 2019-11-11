package edu.wofford.wocoin;
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


        db.CreateWallet("kara");

        assertTrue(db.walletExists("kara"));


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


        db.CreateWallet("kara");


        assertTrue(db.walletExists("kara"));

        db.CreateWallet("kara");

        assertTrue(db.walletExists("kara"));


    }
/*
    @Test
    public void testAddWalletToANonExistentUser(){
        Database db =new Database("src/test/resources/testdb.db");
        Wallet w = new Wallet ("i dont exist");
        assertTrue(!w.WalletExists());
    }
*/
    @Test
    public void testAddWalletPathDNE(){

    }

    @Test
    public void testPublicKeyIsNonEmptyAndGood(){

    }






}