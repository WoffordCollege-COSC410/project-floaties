package edu.wofford.wocoin;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;
import java.io.*;
import java.sql.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.DefaultBlockParameterName;

public class Database {
    private String adminPwd = "adminpwd";
    private String url;
    private Connection con;
    public boolean detectsExisting;
    public int salt = this.generateSalt();

    /**
     * This is the constructor for db
     * @param fileName relative pathname of database to be created
     */
    public Database(String fileName) {
        adminPwd = "adminpwd";
        url = "jdbc:sqlite:" + fileName;

        if(!doesExist(fileName)){
            Utilities.createNewDatabase(fileName);
        }



    }


    /**
     *ignore below function we could never get it to work
     * @param path relative pathname of DB
     * @return boolean if file exists
     */
    public boolean doesExist(String path) {

        File file = new File(path);
        if(!file.exists()) {
            detectsExisting = false;
        } else {
            detectsExisting = true;
        }
        return detectsExisting;
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
    //stub
    public boolean userExists() {
        return false;
    }

    /*private boolean userExists(String username) {
        String user ="";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()){


            String testQuery = String.format("SELECT id FROM users WHERE id = %s;", username);

            ResultSet rs = stmt.executeQuery(testQuery);
            while(rs.next()){
                user = rs.getString(1);
            }


            if (!(user.equals(null))) {  //.wasNull
                return false;
            } else {
                return true;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return true;
        }
    }*/

    /**
     *currently not working depends on the above UserExists method that is not correctly working
     * we know this because of the code coverage report that does not go inside the main if of the function
     * @param id - this will be the value stored in the id column of the Database
     * @param password - this value will be salted and hashed and stored in the salt and hash columns of the DB
     * @return boolean - if user exists or not
     */

    public boolean addUser(String id, String password) {
        if(!userExists()){
            String saltedPasswd;
            //int salt = generateSalt();
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
        String hash = "";
        hash = Utilities.applySha256(saltedPasswd);

        return hash;
    }


}
