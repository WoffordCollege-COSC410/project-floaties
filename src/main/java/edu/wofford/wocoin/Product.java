package edu.wofford.wocoin;

/**
 * Class that defines the product object.
 */
public class Product {

    private String seller;
    private int price;
    private String name;
    private String description;

    /**
     * This constructor sets the seller, price, name, and description for the product.
     * @param seller publicKey of user the product is created under
     * @param price price of product
     * @param name name of product
     * @param description description of product
     */
    public Product(String seller, int price, String name, String description){
        this.seller = seller;
        this.price = price;
        this.name = name;
        this.description = description;
    }

    /**
     * Converts the product into a readable format.
     * @return Product listed as Name: Description [Price WoCoins].
     */
    public String toString(){
        if(price == 1){
            return name + ": " + description + "  [" + price + " WoCoin]";
        } else {
            return name + ": " + description + "  [" + price + " WoCoins]";
        }

    }

    /**
     * Returns the seller of the product.
     * @return seller/publicKey
     */
    public String getSeller(){
        return seller;
    }

}
