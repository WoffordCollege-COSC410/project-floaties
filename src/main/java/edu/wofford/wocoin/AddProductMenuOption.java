package edu.wofford.wocoin;

public class AddProductMenuOption extends MenuOption {
    public AddProductMenuOption(){
        trigger = "";
        triggerText = "add product";
    }

    @Override
    public void execute(){
        if(db.walletExists(username)){
            terminal.println("Enter product name: ");
            String name = scan.nextLine();
            while(name.length() == 0){
                terminal.println("Invalid value.");
                terminal.println("Expected a string with at least 1 character.");
                terminal.println("Enter product name: ");
                name = scan.nextLine();
            }
            terminal.println("Enter product description: ");
            String description = scan.nextLine();
            while(description.length() == 0){
                terminal.println("Invalid value.");
                terminal.println("Expected a string with at least 1 character.");
                terminal.println("Enter product description: ");
                description = scan.nextLine();
            }
            terminal.println("Enter price: ");
            int price = Integer.parseInt(scan.nextLine());
            while(price < 1){
                terminal.println("Invalid value.");
                terminal.println("Expected an integer value greater than or equal to 1.");
                terminal.println("Enter product price greater than 1: ");
                price = Integer.parseInt(scan.nextLine());
            }

            String seller = db.turnIdtoPublickey(username);
            db.addProduct(seller, price, name, description);
            terminal.println("Product added.");

        } else {
            terminal.println("User has no wallet.");
        }
    }


}
