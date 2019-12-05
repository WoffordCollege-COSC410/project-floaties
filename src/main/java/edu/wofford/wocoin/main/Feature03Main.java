package edu.wofford.wocoin.main;

import edu.wofford.wocoin.*;


import edu.wofford.wocoin.Database;

import java.io.OutputStream;
import java.util.Scanner;
import java.lang.Integer;
import java.io.PrintStream;

public class Feature03Main {

    public static void main(String[] args) {
        Database d = new Database(args[0]);
        Scanner k = new Scanner(System.in);
        PrintStream t = new PrintStream(System.out);

        Menu menu = new Menu(d, k, t);


        menu.addAdminOption(new AddUserMenuOption());
        menu.addAdminOption(new RemoveUserMenuOption());

        menu.addUserOption(new CreateWalletMenuOption());
        //menu.addUserOption(new AddProductMenuOption());


        menu.display();
        /*boolean menuContinue = true;
        boolean userMenuContinue = true;

        Database d = new Database(args[0]);
        Scanner scan = new Scanner(System.in);



        while (menuContinue) {
            System.out.println("1: exit");
            System.out.println("2: administrator");
            System.out.println("3: user");

            String rootMenu = scan.nextLine();

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
                                System.out.println("Put username: ");
                                String username = scan.nextLine();
                                System.out.println("Password: ");
                                String userPass = scan.nextLine();
                                if (d.addUser(username, userPass)) {
                                    System.out.println(username + " was added.");
                                    menuContinue = false;
                                    break;
                                } else {
                                    System.out.println(username + " already exists.");
                                    menuContinue = false;
                                    break;
                                }

                            case "3":
                                System.out.println("Username please: ");
                                String removeUser = scan.nextLine();
                                d.removeUser(removeUser);
                                System.out.println(removeUser + " was removed.");
                                menuContinue = false;
                                break;
                        }
                    } else {
                        System.out.println("Incorrect administrator password.");
                        menuContinue = false;
                        break;
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
                                            userMenuContinue = false;
                                            menuContinue = false;
                                            break;
                                        } else {
                                            System.out.println("Action canceled.");
                                            userMenuContinue = false;
                                            menuContinue = false;
                                            break;
                                        }

                                    } else {
                                        d.createWallet(user);
                                        System.out.println("Wallet added.");
                                        userMenuContinue = false;
                                        menuContinue = false;
                                        break;
                                    }


                            }
                            //i added below else
                        } else {
                            System.out.println("No such user.");
                            userMenuContinue = false;
                            menuContinue = false;
                            break;
                        }

                    }
            }
                    break;



        }
    }*/
    }
}
