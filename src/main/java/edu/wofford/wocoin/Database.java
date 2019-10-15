package edu.wofford.wocoin;// import package
// import sqlite library

import java.io.FileNotFoundException;

public class Database {
    private String adminPwd = "adminpwd";

    public Database() {
        this.adminPwd = adminPwd;
    }

    public Database(String path){
        /* try{
            if(sqlite3_open() == 0){

            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }*/
    }


    public boolean checkIsAdmin(String password) {
        return password.equals(adminPwd);
    }
}


// constructor and is admin

// boolean check if user is in database

//  boolean add user

// remove user



