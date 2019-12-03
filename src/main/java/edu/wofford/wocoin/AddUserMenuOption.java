package edu.wofford.wocoin;

public class AddUserMenuOption extends MenuOption {

    public AddUserMenuOption() {
        this.triggerText = "add user";
        this.trigger = "";
    }

    public void execute() {
        terminal.println("Username: ");
        String username = scan.nextLine();
        terminal.println("Password: ");
        String userPass = scan.nextLine();
        if (db.addUser(username, userPass)) {
            terminal.println(username + " was added.");
        } else {
            terminal.println(username + " already exists.");
        }
    }
}

