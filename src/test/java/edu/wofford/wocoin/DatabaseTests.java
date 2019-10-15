package edu.wofford.wocoin;

import org.junit.Test;
import java.io.*;
import java.nio.file.*;

import static org.testng.Assert.assertEquals;

public class DatabaseTests {

    @Test
    public void databaseInstantiated() {

    }

    @Test
    public void isAdmin() {
        Database d = new Database();
        String pwd = "adminPwd";
        assertEquals(true, d.checkIsAdmin(pwd));
    }

    @Test
    public void isNotAdmin() {
        String pwd = "notThePwd";
        Database d = new Database();
        assertEquals(false, d.checkIsAdmin(pwd));
    }
}
