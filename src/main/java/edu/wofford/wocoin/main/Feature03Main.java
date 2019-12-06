package edu.wofford.wocoin.main;

import edu.wofford.wocoin.*;


import edu.wofford.wocoin.Database;

import java.util.Scanner;
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

        menu.display();
    }
}
