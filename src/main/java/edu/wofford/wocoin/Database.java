package edu.wofford.wocoin;

import java.io.*;
import java.sql.*;

public class Database {
    private String adminPwd;
    private String url;
    private Connection con;

    /**
     * This is the constructor for db
     * @param fileName relative pathname of database to be created
     */
    public Database(String fileName) {
        adminPwd = "adminpwd";
        url = "jdbc:sqlite:" + fileName;

        File file = new File(fileName);
        if(!file.exists()){
            Utilities.createNewDatabase(fileName);
            //Utilities.createTestDatabase(fileName);
        }



    }

    /**
     * returns the administrators password
     */

    public  String getAdminPwd(){
        return adminPwd;
    }

    /**
     * returns TRUE if the caller's input is administrator
     * @param password - user input to check against
     * @return boolean
     */

    public boolean checkIsAdmin(String password){
        if(password.equals(getAdminPwd())){
            return true;
        }
        else{
            return false;
        }


    }
    /**
     *helper method to add user currently not working
     * @return boolean if the user exists
     */

    private boolean userExists(String id) {

        //link this to add user

       // String testQuery = "SELECT id FROM users WHERE id = ?;";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement("SELECT id FROM users WHERE id = ?;")){

                System.out.println("made the connection");
                 stmt.setString(1,id);
                System.out.println("after setString");
                 ResultSet rs = stmt.executeQuery(/*"SELECT id FROM users WHERE id = ?;"*/); //does this take a string or no
                 System.out.println("after executeQuery is stored");
                 rs.next(); //is this not working bc were adding to an existing database thats not empty but rs.last doesnt work
                 System.out.println("cursor moved");
                 if(rs.getNString(1).equals(id)){
                     System.out.println("string = id");
                     return true;
                 }
                 else{
                     System.out.println("string != id");
                     return false;
                 }
        }
        catch(SQLException e){
            System.out.println("throws exception");
            e.printStackTrace();
            return true;
        }
    }

//    private boolean userExists(String username) {
//        return false;
//    }

    /**
     *currently not working depends on the above UserExists method that is not correctly working
     * we know this because of the code coverage report that does not go inside the main if of the function
     * @param id - this will be the value stored in the id column of the Database
     * @param password - this value will be salted and hashed and stored in the salt and hash columns of the DB
     * @return boolean - if user exists or not
     */

    public boolean addUser(String id, String password) {
        if(!userExists(id)){
            System.out.println("i'm in the if statement");
            String saltedPasswd;
            int salt = generateSalt();
            saltedPasswd = getSaltedPasswd(password, salt);
            String hash = getHash(saltedPasswd);

            String testQuery = "INSERT INTO users (id, salt, hash) VALUES (?, ?, ?);";


            try (Connection conn= DriverManager.getConnection(url);
                 PreparedStatement stmt = conn.prepareStatement(testQuery)){

                stmt.setString(1, id);
                stmt.setInt(2, salt);
                stmt.setString(3, hash);
                stmt.executeUpdate();

                return true;
            }
            catch(SQLException e){
                e.printStackTrace();
                return false;
            }
        }
        else{
            System.out.println("I didn't get into the if statement");
            return false;
        }

    }

    /**
     * Salts the username
     * @return a random salt value
     */
    private int generateSalt(){
        int saltValue = Utilities.generateSalt();
        return saltValue;
    }

    /**
     * Creates salted password
     * @param passWd - the plain text password passed into the functions initially
     * @param saltValue - a random int that is converted to a string
     * @return passWd concatenated with SaltValue
     */

    private String getSaltedPasswd(String passWd, int saltValue){

        String SaltValueString = Integer.toString(saltValue);
        String builder = passWd + SaltValueString;
        return builder;

    }

    /**
     * Creates hash of salted password
     * @param saltedPasswd - this is the value returned from the above function
     * @return a string that will be stored in the hash field of the DB
     */
    private String getHash(String saltedPasswd){
        return Utilities.applySha256(saltedPasswd);
    }


}
