package edu.wofford.wocoin;

import java.util.Scanner;
import java.io.PrintStream;
import java.util.List;


public class Menu {
    public Menu(Database d, Scanner k, PrintStream t) {
        db = d;
        keyboard = k;
        terminal = t;
    }

    public void display() {
        boolean menuContinue = true;
        while (menuContinue) {
            terminal.println("1: exit");
            terminal.println("2: administrator");
            terminal.println("3: user");

            String rootMenu = keyboard.nextLine();

            switch (rootMenu) {
                case "1":
                    menuContinue = false;
                    break;
                case "2":
                    displayAdminSubmenu();
                    break;

                case "3":
                    displayUserSubmenu();
                    break;
            }

        }
    }


    public void addAdminOption(MenuOption opt) {
        opt.setDatabase(db);
        opt.setKeyboard(keyboard);
        //opt.setTriggerText();
    }


    private void displayAdminSubmenu() {
        terminal.println("Enter password: ");
        String pwd = keyboard.nextLine();
        if (db.checkIsAdmin(pwd)) {
            boolean menuContinue = true;
            while (menuContinue) {
                terminal.println("1: back");


                for (MenuOption opt : adminOptions) {
                    terminal.println(opt.toString());
                }
                String adminMenu = keyboard.nextLine();
                if (adminMenu.equals("1")) {
                    menuContinue = false;
                } else {
                    for (MenuOption opt : adminOptions) {
                        if (opt.isTriggered(adminMenu)) {
                            opt.execute();
                        }
                    }
                }





                terminal.println("2: add user");
                terminal.println("3: remove user");
                //String adminMenu = keyboard.nextLine();

                switch (adminMenu) {
                    case "1":
                        menuContinue = false;
                        break;
                    case "2":
                        //AddUserMenuOption.execute();
                    case "3":
                        terminal.println("Username please: ");
                        String removeUser = keyboard.nextLine();
                        db.removeUser(removeUser);
                        terminal.println(removeUser + " was removed.");
                        break;
                }
            }
        } else {
            terminal.println("Incorrect administrator password.");
        }

    }

    private void displayUserSubmenu() {
        boolean menuContinue = true;
        while (menuContinue) {

            terminal.println("Username: ");
            String user = keyboard.nextLine();
            terminal.println("Password: ");
            String password = keyboard.nextLine();


            if (db.userExists(user) && db.passwordCorrect(user, password)) {
                terminal.println("1: back");
                terminal.println("2: create wallet");
                terminal.println("3: add product");
                terminal.println("4: remove product");
                terminal.println("5: display products");

                String userMenu = keyboard.nextLine();

                switch (userMenu) {
                    case "1":
                        menuContinue = false;
                        break;
                    case "2":
                        if (db.walletExists(user)) {
                            terminal.println("Would you like to replace the existing wallet?");
                            if (keyboard.nextLine().equals("y")) {
                                db.createWallet(user);
                                terminal.println("Wallet added.");
                                menuContinue = false;
                            } else {
                                terminal.println("Action canceled.");
                                menuContinue = false;
                            }

                        } else {
                            db.createWallet(user);
                            terminal.println("Wallet added.");
                            menuContinue = false;
                        }
                        break;
                    case "3":
                        if(db.walletExists(user)){
                            terminal.println("Enter product name: ");
                            String name = keyboard.nextLine();
                            while(name.length() == 0){
                                terminal.println("Invalid value.");
                                terminal.println("Expected a string with at least 1 character.");
                                terminal.println("Enter product name: ");
                                name = keyboard.nextLine();
                            }
                            terminal.println("Enter product description: ");
                            String description = keyboard.nextLine();
                            while(description.length() == 0){
                                terminal.println("Invalid value.");
                                terminal.println("Expected a string with at least 1 character.");
                                terminal.println("Enter product description: ");
                                description = keyboard.nextLine();
                            }
                            terminal.println("Enter price: ");
                            int price = Integer.parseInt(keyboard.nextLine());
                            while(price < 1){
                                terminal.println("Invalid value.");
                                terminal.println("Expected an integer value greater than or equal to 1.");
                                terminal.println("Enter product price greater than 1: ");
                                price = Integer.parseInt(keyboard.nextLine());
                            }

                            String seller = db.turnIdtoPublickey(user);
                            db.addProduct(seller, price, name, description);
                            terminal.println("Product added.");
                            menuContinue = false;
                        } else {
                            terminal.println("User has no wallet.");
                            menuContinue = false;
                        }
                        break;
                }
            } else {
                terminal.println("No such user.");
                menuContinue = false;
            }
        }

    }


    private Database db;
    private Scanner keyboard;
    private PrintStream terminal;
    private List<MenuOption> adminOptions;
}
