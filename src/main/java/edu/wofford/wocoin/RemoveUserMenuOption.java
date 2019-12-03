package edu.wofford.wocoin;


public class RemoveUserMenuOption extends MenuOption {

    public RemoveUserMenuOption() {
        this.triggerText = "remove user";
        this.trigger = "";
    }
    public void execute(){
        System.out.println("Username please: ");
        String removeUser = scan.nextLine();
        db.removeUser(removeUser);
        System.out.println(removeUser + " was removed.");
    }
}
