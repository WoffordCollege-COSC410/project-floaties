package edu.wofford.wocoin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Products extends Database {

    String id;
    String url;
    /**
     * This is the constructor for db
     *
     * @param fileName relative pathname of database to be created
     */
    public Products(String fileName, String id) {
        super(fileName);
        this.id = id;
        this.url = "jdbc:sqlite:" + fileName;
        System.out.println(url);
    }

    boolean walletExists = true; //stub

    //boolean userExists = true; //stub

    private boolean isValidName(String name){
        return !name.equals("");
    }
    private boolean isValidPrice(int price){
        return price > 0;
    }
    private boolean isValidDescription(String description){
        return !description.equals("");
    }



    public boolean addProduct(String seller, int price, String name, String description){

        if(userExists(id) && walletExists){ //change stubs to real methods from parent classes
            if(isValidName(name) && isValidPrice(price) && isValidDescription(description)){

                String testQuery = "INSERT INTO products (seller, price, name, description) VALUES (?, ?, ?, ?);";

                try (Connection conn= DriverManager.getConnection(url);
                     PreparedStatement stmt = conn.prepareStatement(testQuery)){

                    stmt.setString(1, seller);
                    stmt.setInt(2, price);
                    stmt.setString(3, name);
                    stmt.setString(4, description);
                    stmt.executeUpdate();
                    System.out.println("stmts set");
                    System.out.println(stmt);
                    return true;
                }
                catch(SQLException e){
                    e.printStackTrace();
                    System.out.println("exception");
                    return false;
                }

            }else{
                System.out.println("name/price/description invalid");
                return false;
            }
        } else {
            System.out.println("user doesn't exist");
            return false;
        }

    }

    public boolean isActiveWallet(String username){
        return true;
    }
}
