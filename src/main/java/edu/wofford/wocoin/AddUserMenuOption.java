package edu.wofford.wocoin;

import java.io.PrintStream;
import java.util.Scanner;

public class AddUserMenuOption extends MenuOption {

    Scanner keyboard;
    PrintStream terminal;

    public AddUserMenuOption() {
        this.triggerText = "add user";
        this.trigger = "";
    }

    public void execute() {
        terminal.println("Username: ");
        String username = keyboard.nextLine();
        terminal.println("Password: ");
        String userPass = keyboard.nextLine();
        if (db.addUser(username, userPass)) {
            terminal.println(username + " was added.");
        } else {
            terminal.println(username + " already exists.");
        }
    }
}

