package edu.wofford.wocoin;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DisplayProductMenuOption extends MenuOption {
    //List<String> list = new ArrayList<String>();
    List<Product> list;

    public DisplayProductMenuOption(String trigger, String triggerText) {
        super(trigger, triggerText);
    }
    public void execute() {
        System.out.println("Username: ");
        String user = scan.nextLine();
        System.out.println("Password: ");
        String password = scan.nextLine();
        list = super.db.displayProductF6();
        String userWallet = super.db.turnIdtoPublickey(user);

        for(int i = 1; i<= list.size(); i++){
            if(list.get(i).getSeller().equals(userWallet)){
                System.out.println(i + ">>>" + list.get(i).toString());
            }else{
                System.out.println(i + list.get(i).toString());
            }

        }
    }
}
