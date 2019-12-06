package edu.wofford.wocoin;


public class CreateWalletMenuOption extends MenuOption{

    public CreateWalletMenuOption() {
        this.triggerText = "create wallet";
        this.trigger = "";
    }

    @Override
    public void execute() {
//        System.out.println("1");
//        System.out.println(username);
        terminal.println("Where would you like to create this wallet?");
        String directory = scan.nextLine();
        if(db.userExists(username)){
            if (db.walletExists(username)) {
                terminal.println("Would you like to replace the existing wallet? 'y' or 'n'");
                if (scan.nextLine().equals("y")) {
                    if(db.createWallet(username, directory, password)){
                        terminal.println("Wallet added.");
                    }else{
                        terminal.println("wallet not created");
                    }
                } else {
                    terminal.println("Action canceled.");
                }

            } else {
                //System.out.println("2");
                if(db.createWallet(username, directory, password)){
                    terminal.println("Wallet added.");
                }
            }
        } else {
            terminal.println("User does not exist.");
        }

    }
}
