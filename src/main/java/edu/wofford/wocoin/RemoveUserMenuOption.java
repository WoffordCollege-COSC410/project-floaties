package edu.wofford.wocoin;

import java.io.PrintStream;
import java.util.Scanner;

public class RemoveUserMenuOption extends MenuOption {

    Scanner keyboard;
    PrintStream terminal;

    public RemoveUserMenuOption() {
        this.triggerText = "remove user";
        this.trigger = "";
    }
    public void execute(){
        System.out.println("Username please: ");
        String removeUser = scan.nextLine();
        db.removeUser(removeUser);
        System.out.println(removeUser + " was removed.");
    }
}
