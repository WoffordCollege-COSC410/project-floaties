package edu.wofford.wocoin;
import edu.wofford.wocoin.main.*;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Test;
import java.io.*;
import java.net.ConnectException;
import java.nio.file.*;
import java.sql.*;
import java.util.concurrent.ExecutionException;
import javax.crypto.Cipher;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import org.web3j.crypto.CipherException;



public class DisplayAccountBalanceTest{
    @Test
    public void displayCreatedAccountBalanceTest() throws IOException, InterruptedException, ExecutionException {
        String fileName = "src/test/resources/testdb.db";
        String destName = "src/test/resources/testdbcopy.db";
        File file = new File(fileName);
        File dest = new File(destName);
        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        assertTrue(file.exists());
        Database db = new Database(destName);
        db.addUser("Quavo", "WhipItAtTheBandoBoySKRAAAAAAAAAAAAAA");
        db.createWallet("Quavo", destName, "WhipItAtTheBandoBoySKRAAAAAAAAAAAAAA");
        db.sendTransaction("Quavo" , 42069);
        try{
            assertEquals("User has " + 42069 + " WoCoins.", db.displayAccountBalance("Quavo"));
        }
        catch(CipherException | ConnectException e){
            System.out.println("e");
        }

        dest.delete();
    }
}