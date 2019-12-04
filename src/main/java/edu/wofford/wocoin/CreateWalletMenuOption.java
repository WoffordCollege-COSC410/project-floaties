package edu.wofford.wocoin;


public class CreateWalletMenuOption extends MenuOption{

    public CreateWalletMenuOption() {
        this.triggerText = "create wallet";
        this.trigger = "";
    }

    @Override
    public void execute() {
        if (db.walletExists(username)) {
            terminal.println("Would you like to replace the existing wallet?");
            if (scan.nextLine().equals("y")) {
                db.createWallet(username);
                terminal.println("Wallet added.");
            } else {
                terminal.println("Action canceled.");
            }

        } else {
            db.createWallet(username);
            terminal.println("Wallet added.");
        }
    }
}
