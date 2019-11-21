package edu.wofford.wocoin;

public class AddUserMenuOption extends MenuOption {
    public void execute() {
        terminal.println("Username: ");
        String username = keyboard.nextLine();
        terminal.println("Password: ");
        String userPass = keyboard.nextLine();
        if (db.addUser(username, userPass)) {
            terminal.println(username + " was added.");
        } else {
            terminal.println(username + " already exists.");
        }
    }
}
