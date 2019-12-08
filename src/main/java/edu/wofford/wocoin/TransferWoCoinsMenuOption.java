package edu.wofford.wocoin;

import edu.wofford.wocoin.MenuOption;

public class TransferWoCoinsMenuOption extends MenuOption {
    public TransferWoCoinsMenuOption() {
        this.triggerText = "transfer WoCoins";
        this.trigger = "";
    }

    @Override
    public void execute() {
        terminal.println("Who would you like to send WoCoins?");
        String user = scan.nextLine();
        if (db.userExists(user) ) {
            if (db.walletExists(user)){
                terminal.println("How many WoCoins would you like to transfer to " + user + "?");
                int amount = scan.nextInt();
                if (amount > 0){
                    if(db.sendTransaction(user, amount)){
                        terminal.println("Transfer complete.");
                    }
                }else{
                    terminal.println("Invalid value.");
                    terminal.println("Expected an integer value greater than or equal to 1.");
                    // go back to admin menu
                }

            }else{
                terminal.println("User has no wallet.");
                // go back to admin menu
            }



        } else {
            terminal.println("No such user");
        }
    }
}
