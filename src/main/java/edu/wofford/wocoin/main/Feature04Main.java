package edu.wofford.wocoin.main;

import edu.wofford.wocoin.Database;

import java.util.Scanner;

public class Feature04Main {

    public static void main(String[] args) {
        boolean menuContinue = true;
        boolean userMenuContinue = true;

        Database d = new Database(args[0]);
        Scanner scan = new Scanner(System.in);

        System.out.println("1: exit");
        System.out.println("2: administrator");
        System.out.println("3: user");

        String rootMenu = scan.nextLine();

        while (menuContinue) {
            switch (rootMenu) {
                case "1":
                    menuContinue = false;
                    break;
                case "2":
                    System.out.println("Enter password: ");
                    String pwd = scan.nextLine();
                    if (d.checkIsAdmin(pwd)) {
                        System.out.println("1: back");
                        System.out.println("2: add user");
                        System.out.println("3: remove user");
                        String adminMenu = scan.nextLine();

                        switch (adminMenu) {
                            case "1":
                                break;
                            case "2":
                                System.out.println("Username: ");
                                String username = scan.nextLine();
                                System.out.println("Password: ");
                                String userPass = scan.nextLine();
                                d.addUser(username, userPass);
                                System.out.println(username + " was added.");
                                menuContinue = false;
                                break;
                            case "3":
                                System.out.println("Username please: ");
                                String removeUser = scan.nextLine();
                                d.removeUser(removeUser);
                                System.out.println(removeUser + " was removed.");
                                menuContinue = false;
                                break;
                        }
                    }
                    break;
                case "3":
                    while (userMenuContinue) {

                        System.out.println("Username: ");
                        String user = scan.nextLine();
                        System.out.println("Password: ");
                        String password = scan.nextLine();


                        if (d.userExists(user) && d.passwordCorrect(user, password)) {
                            System.out.println("1: back");
                            System.out.println("2: create wallet");
                            System.out.println("3: add product");
                            String userMenu = scan.nextLine();

                            switch (userMenu) {
                                case "1":
                                    menuContinue = true;
                                    userMenuContinue = false;
                                    break;
                                case "2":
                                    if (d.walletExists(user)) {
                                        System.out.println("Would you like to replace the existing wallet?");
                                        if (scan.nextLine().equals("y")) {
                                            d.createWallet(user);
                                            System.out.println("Wallet added.");
                                        } else if (scan.nextLine().equals("n")) {
                                            System.out.println("Action canceled.");
                                            userMenuContinue = false;
                                            break;
                                        } else {
                                            System.out.println("nah"); //fix
                                        }

                                    }
                                    break;
                                case "3":
                                    //d.addProduct();
                            }

                        } else {
                            System.out.println("No such user.");
                            userMenuContinue = false;
                            menuContinue = false;
                        }

                    }
                    break;
            }

        }
    }
}

