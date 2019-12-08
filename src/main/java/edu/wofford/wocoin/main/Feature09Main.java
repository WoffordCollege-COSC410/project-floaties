package edu.wofford.wocoin.main;

import edu.wofford.wocoin.*;

import java.io.PrintStream;
import java.util.Scanner;

public class Feature09Main {
    public static void main(String[] args) {

        Database d = new Database(args[0]);
        Scanner k = new Scanner(System.in);
        PrintStream t = new PrintStream(System.out);

        Menu menu = new Menu(d, k, t);


        menu.addAdminOption(new AddUserMenuOption());
        menu.addAdminOption(new RemoveUserMenuOption());
        menu.addAdminOption(new TransferWoCoinsMenuOption());

        menu.addUserOption(new CreateWalletMenuOption());
        menu.addUserOption(new AddProductMenuOption());
        menu.addUserOption(new RemoveProductMenuOption());
        menu.addUserOption(new DisplayProductMenuOption());

        menu.display();

    }
}
