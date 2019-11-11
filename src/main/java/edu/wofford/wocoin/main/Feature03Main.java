package edu.wofford.wocoin.main;

import edu.wofford.wocoin.Database;

import java.util.Scanner;

public class Feature03Main {

    public static void main(String[] args) {
//        boolean menuContinue = true;
//        Database d = new Database(args[0]);
//        Scanner scan = new Scanner(System.in);
//
//        System.out.println("1: exit");
//        System.out.println("2: administrator");
//        System.out.println("3: user");
//
//        String rootMenu = scan.nextLine();
//
//        while (menuContinue) {
//            do switch (rootMenu) {
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
//                                d.addUser(username, userPass);
//                            case "3":
//                                System.out.println("Username: ");
//                                String removeUser = scan.nextLine();
//                                d.removeUser(removeUser);
//                        }
//
//                    }
//                case "3":
//                    boolean userMenuContinue = true;
//
//                    System.out.println("Username: ");
//                    String user = scan.nextLine();
//                    System.out.println("Password: ");
//                    String password = scan.nextLine();
//
//                    if (d.userExists(user) && d.passwordCorrect(user, password)) {
//                        System.out.println("1: back");
//                        System.out.println("2: create wallet");
//                        String userMenu = scan.nextLine();
//
//                        switch (userMenu) {
//                            case "1":
//                                menuContinue = false;
//                                break;
//                            case "2":
//                                if (d.walletExists()) {
//                                    System.out.println("Would you like to replace the existing wallet?");
//                                    if (scan.nextLine().equals("y")) {
//                                        d.createWallet(user);
//                                    } else if (scan.nextLine().equals("n")) {
//                                        System.out.println("Action canceled.");
//                                        userMenuContinue = false;
//                                        break;
//                                    } else {
//                                        System.out.println("nah"); //fix
//                                    }
//
//                                }
//
//                        }
//                    } else {
//                        System.out.println("No such user.");
//                        userMenuContinue = false;
//                    }
//            }
//            }
        }
    }
