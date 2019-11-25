package edu.wofford.wocoin;

public class Product {

    private String seller;
    private int price;
    private String name;
    private String carrats;

    private String description;

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

 //   public String carrats(){

   // }
}
