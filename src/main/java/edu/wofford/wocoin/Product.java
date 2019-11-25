package edu.wofford.wocoin;

public class Product {

    public String seller;
    public int price;
    public String name;
    public String description;

    public Product(String seller, int price, String name, String description){
        this.seller = seller;
        this.price = price;
        this.name = name;
        this.description = description;
    }

    public String toString(){
        if(price == 1){
            return name + ":" + description + "[" + price + "WoCoin]";
        } else {
            return name + ":" + description + "[" + price + "WoCoins]";
        }

    }
}