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
        opt.setTerminal(terminal);
        adminOptions.add(opt);
        //opt.setTriggerText();
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
                String adminMenu = keyboard.nextLine();
                if (adminMenu.equals("1")) {
                    adminMenuContinue = false;
                } else if (adminMenu.equals("4")){
                    String directory = adminMenu;
                    String adminMenu2 = keyboard.nextLine();
                    adminOptions.get(Integer.parseInt(adminMenu2) - 2).execute();
                    adminMenuContinue = false;
                } else {
                    adminOptions.get(Integer.parseInt(adminMenu) - 2).execute();
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
                    String userMenu = keyboard.nextLine();
                    if (userMenu.equals("1")) {
                        userMenuContinue = false;
                    } else if(userMenu.equals("2")){
                        int optInt = Integer.parseInt(userMenu);
                        if( optInt == 2){
                            userOptions.get(optInt - 2).execute();
                        } else{
                            userOptions.get(0).execute();
                        }
                        userMenuContinue = false;
                    } else{
                        userOptions.get(Integer.parseInt(userMenu) - 2).execute();
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
