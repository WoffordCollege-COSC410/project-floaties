
package edu.wofford.wocoin.main;

import edu.wofford.wocoin.Database;
//import edu.wofford.wocoin.MenuOption;

import java.util.Scanner;
import java.lang.Integer;

public class Feature05Main {

    public static void main(String[] args) {
        boolean menuContinue = true;
        boolean userMenuContinue = true;

        Database d = new Database(args[0]);
        Scanner scan = new Scanner(System.in);




//        while (menuContinue) {
//            System.out.println("1: exit");
//            System.out.println("2: administrator");
//            System.out.println("3: user");
//
//            String rootMenu = scan.nextLine();
//
//            switch (rootMenu) {
//                case "1":
//                    menuContinue = false;
//                    break;
//                case "2":
//                    System.out.println("Enter password: ");
//                    String pwd = scan.nextLine();
//                    if (d.checkIsAdmin(pwd)) {
//                        System.out.println("1: back");
//                        System.out.println("2: add user");
//                        System.out.println("3: remove user");
//                        String adminMenu = scan.nextLine();
//
//                        switch (adminMenu) {
//                            case "1":
//                                break;
//                            case "2":
//                                System.out.println("Username: ");
//                                String username = scan.nextLine();
//                                System.out.println("Password: ");
//                                String userPass = scan.nextLine();
//                                if (d.addUser(username, userPass)) {
//                                    System.out.println(username + " was added.");
//                                    menuContinue = false;
//                                    break;
//                                } else {
//                                    System.out.println(username + " already exists.");
//                                    menuContinue = false;
//                                    break;
//                                }
//
//
//                            case "3":
//                                System.out.println("Username please: ");
//                                String removeUser = scan.nextLine();
//                                d.removeUser(removeUser);
//                                System.out.println(removeUser + " was removed.");
//                                menuContinue = false;
//                                break;
//                        }
//                    } else {
//                        System.out.println("Incorrect administrator password.");
//                        menuContinue = false;
//                        break;
//                    }
//                    break;
//
//                case "3":
//                    while (userMenuContinue) {
//
//                        System.out.println("Username: ");
//                        String user = scan.nextLine();
//                        System.out.println("Password: ");
//                        String password = scan.nextLine();
//
//
//                        if (d.userExists(user) && d.passwordCorrect(user, password)) {
//                            System.out.println("1: back");
//                            System.out.println("2: create wallet");
//                            System.out.println("3: add product");
//                            System.out.println("4: remove product");
//
//                            String userMenu = scan.nextLine();
//
//                            switch (userMenu) {
//                                case "1":
//                                    menuContinue = true;
//                                    userMenuContinue = false;
//                                    break;
//                                case "2":
//                                    if (d.walletExists(user)) {
//                                        System.out.println("Would you like to replace the existing wallet?");
//                                        if (scan.nextLine().equals("y")) {
//                                            d.createWallet(user);
//                                            System.out.println("Wallet added.");
//                                            userMenuContinue = false;
//                                            menuContinue = false;
//                                            break;
//                                        } else {
//                                            System.out.println("Action canceled.");
//                                            userMenuContinue = false;
//                                            menuContinue = false;
//                                            break;
//                                        }
//
//                                    } else {
//                                        d.createWallet(user);
//                                        System.out.println("Wallet added.");
//                                        userMenuContinue = false;
//                                        menuContinue = false;
//                                        break;
//                                    }
//                                case "3":
//                                    if(d.walletExists(user)){
//                                        System.out.println("Enter product name: ");
//                                        String name = scan.nextLine();
//                                        while(name.length() == 0){
//                                            System.out.println("Invalid value.");
//                                            System.out.println("Expected a string with at least 1 character.");
//                                            System.out.println("Enter product name: ");
//                                            name = scan.nextLine();
//                                        }
//                                        System.out.println("Enter product description: ");
//                                        String description = scan.nextLine();
//                                        while(description.length() == 0){
//                                            System.out.println("Invalid value.");
//                                            System.out.println("Expected a string with at least 1 character.");
//                                            System.out.println("Enter product description: ");
//                                            description = scan.nextLine();
//                                        }
//                                        System.out.println("Enter price: ");
//                                        int price = Integer.parseInt(scan.nextLine());
//                                        while(price < 1){
//                                            System.out.println("Invalid value.");
//                                            System.out.println("Expected an integer value greater than or equal to 1.");
//                                            System.out.println("Enter product price greater than 1: ");
//                                            price = Integer.parseInt(scan.nextLine());
//                                        }
//
//                                        String seller = d.turnIdtoPublickey(user);
//                                        d.addProduct(seller, price, name, description);
//                                        System.out.println("Product added.");
//                                        menuContinue = false;
//                                        userMenuContinue = false;
//                                        break;
//                                    } else {
//                                        System.out.println("User has no wallet.");
//                                        userMenuContinue = false;
//                                        menuContinue = false;
//                                        break;
//                                    }
//                                case "4":
//                                    System.out.println("1: cancel");
//
//
//                                    String productMenu = scan.nextLine();
//
//                                    switch (productMenu){
//                                        case "1":
//                                            menuContinue = true;
//                                            userMenuContinue = false;
//                                            break;
//                                        case "2":
//
//                                    }
//                                    //remove product
//                            }
//
//
//                        } else {
//                            System.out.println("No such user.");
//                            userMenuContinue = false;
//                            menuContinue = false;
//                        }
//                        break;
//                    }
//
//
//            }
//
//        }

    }

}
