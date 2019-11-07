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
    public void testAddWalletToExistingWalletOverwrite(){
        Database db = new Database("src/test/resourcs/testdb.db");
        Wallet w = new Wallet("kara");
        assertTrue(w.WalletExists());
        //might exist in other db I dont like how wallet takes a db file as the second input
        Wallet wa = new Wallet("kara");
        assertTrue(!w.WalletExists());
        assertTrue(wa.WalletExists());

    }



    @Test
    public void testAddWalletToExistingWalletNotOverwrite(){
        Database db = new Database("src/test/resourcs/testdb.db");
        Wallet w = new Wallet("kara");
         //once this
        assertTrue(w.WalletExists());
        //might exist in other db I dont like how wallet takes a db file as the second input
        Wallet wa = new Wallet("kara");
        assertTrue(w.WalletExists());
        assertTrue(!wa.WalletExists());

    }

    @Test
    public void testAddWalletToANonExistentUser(){
        Database db =new Database("src/test/resources/testdb.db");
        Wallet w = new Wallet ("i dont exist");
        assertTrue(!w.WalletExists());
    }

    @Test
    public void testAddWalletPathDNE(){

    }

    @Test
    public void testPublicKeyIsNonEmptyAndGood(){

    }






}