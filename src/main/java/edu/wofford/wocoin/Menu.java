package edu.wofford.wocoin;

import org.web3j.abi.datatypes.Int;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintStream;
import java.util.List;


public class Menu {
    public Menu(Database d, Scanner k, PrintStream t) {
        db = d;
        keyboard = k;
        terminal = t;
        adminOptions = new ArrayList<>();
        userOptions = new ArrayList<>();
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
                    System.exit(0);
                    break;
                case "2":
                    displayAdminSubmenu();
                    menuContinue = true;
                    break;

                case "3":
                    displayUserSubmenu();
                    menuContinue = true;
                    break;
            }
        }
    }


    public void addAdminOption(MenuOption opt) {
        opt.setDatabase(db);
        opt.setKeyboard(keyboard);
        opt.setTerminal(terminal);
        adminOptions.add(opt);
    }

    public void addUserOption(MenuOption opt) {
        opt.setDatabase(db);
        opt.setKeyboard(keyboard);
        opt.setTerminal(terminal);
        userOptions.add(opt);
    }


    private void displayAdminSubmenu() {
        boolean adminMenuContinue = true;
        terminal.println("Enter password: ");
        String pwd = keyboard.nextLine();
        while (adminMenuContinue) {
            if (db.checkIsAdmin(pwd)) {
                terminal.println("1: back");

                int i = 2;
                for (MenuOption opt : adminOptions) {
                    opt.setTriggers(i);
                    opt.setPassword(pwd);
                    terminal.println(opt.toString());
                    i++;
                }
                int adminMenu = keyboard.nextInt();
//                terminal.println("|" + adminMenu + "|");
                keyboard.nextLine();
//                terminal.println("|" + adminMenu + "|");
                if (adminMenu == 1) {
                    adminMenuContinue = false;
//                } else {
//                    adminMenu -= 2;
//                    adminOptions.get(adminMenu).execute();
//                    adminMenuContinue = true;
//                }
                } else if (adminMenu == 2) {
                    adminOptions.get(0).execute();
                    adminMenuContinue = true;
                } else if (adminMenu == 3) {
                    adminOptions.get(1).execute();
                    adminMenuContinue = true;
                } else {
                    adminOptions.get(2).execute();
                    adminMenuContinue = true;
                }

            } else {
                terminal.println("Incorrect administrator password.");
                adminMenuContinue = false;
            }
        }

    }

    private void displayUserSubmenu() {
        boolean userMenuContinue = true;

            terminal.println("Username: ");
            String user = keyboard.nextLine();

            terminal.println("Password: ");
            String password = keyboard.nextLine();

            if (db.userExists(user) && db.passwordCorrect(user, password)) {
                while(userMenuContinue) {
                    terminal.println("1: back");
                    int i = 2;
                    for (MenuOption opt : userOptions) {
                        opt.setUsername(user);
                        opt.setPassword(password);
                        opt.setTriggers(i);
                        terminal.println(opt.toString());
                        i++;
                    }
                    int userMenu = keyboard.nextInt();
                    keyboard.nextLine();
                    if (userMenu == 1) {
                        userMenuContinue = false;
                    } else{
                        userOptions.get(userMenu - 2).execute();
                        userMenuContinue = true;
                    }
                }
            } else {
                terminal.println("No such user.");
                userMenuContinue = false;
            }

    }


    private Database db;
    private Scanner keyboard;
    private PrintStream terminal;
    private List<MenuOption> adminOptions;
    private List<MenuOption> userOptions;
}
