package edu.wofford.wocoin;

import org.junit.Test;

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

        }

    }

    /**
     * returns the administrators password
     */

    public String getAdminPwd(){
        return adminPwd;
    }

    /**
     * returns TRUE if the caller's input is administrator
     * @param password user input to check against
     * @return boolean if the password exists or not
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

    public boolean userExists(String id) {


       // String testQuery = "SELECT id FROM users WHERE id = ?;";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement("SELECT id FROM users WHERE id = ?;")){

                 stmt.setString(1,id);
                 ResultSet rs = stmt.executeQuery();

                 if (rs.next()) {
                     if(rs.getString(1).equals(id)){
                         return true;
                     }
                     else{
                         return false;
                     }
                 } else {
                    return false;
                 }

        }
        catch(SQLException e){
            e.printStackTrace();
            return true;
        }
    }

    /**
     *currently not working depends on the above UserExists method that is not correctly working
     * we know this because of the code coverage report that does not go inside the main if of the function
     * @param id this will be the value stored in the id column of the Database
     * @param password this value will be salted and hashed and stored in the salt and hash columns of the DB
     * @return boolean if user exists or not
     */

    public boolean addUser(String id, String password) {
        if(!userExists(id)){
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
            return false;
        }

    }
    /**
     * Removes a user identified by their id from the Database
     * @param id a value passed in by the user to be removed
     * @return a boolean value of if the user was removed or not
     */
    public boolean removeUser(String id){
        if(userExists(id)){
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE id = ?;")){
                stmt.setString(1,id);
                stmt.executeUpdate();

                return true;
            } catch(SQLException e) {
                e.printStackTrace();
                return false; }
        } else {
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
     * @param saltedPasswd this is the value returned from the above function
     * @return a string that will be stored in the hash field of the DB
     */
    private String getHash(String saltedPasswd){
        return Utilities.applySha256(saltedPasswd);
    }


}
