package edu.wofford.wocoin;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DisplayProductMenuOption extends MenuOption {
    List<Product> list;


    public DisplayProductMenuOption() {
        this.trigger = "";
        this.triggerText = "display product";
        list = new ArrayList<>();
    }
    public void execute() {
        list = super.db.displayProductF6();
        String userWallet = super.db.turnIdtoPublickey(username);
        for(int i = 0; i< list.size(); i++){
            if(list.get(i).getSeller().equals(userWallet)){
                System.out.println(i+1 + ": " + ">>> " + list.get(i).toString());
            }else{
                System.out.println(i+1 + ": " + list.get(i).toString());
            }

        }
    }
}
