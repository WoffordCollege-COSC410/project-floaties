package edu.wofford.wocoin;
import java.util.Scanner;

public abstract class MenuOption {

    protected Database db;
    protected Scanner scan;
    protected String username;
    protected String password;
    protected String trigger;
    protected String triggerText;

    public MenuOption(){
        this.triggerText = "";
        this.trigger = "";
    }

    public MenuOption(String trigger, String triggerText) {
        this.trigger = trigger;
        this.triggerText = triggerText;
    }

    public boolean isTriggered(String s) {
        return true; //stub
    }

    public void setTriggers(int trigger){
        this.trigger = Integer.toString(trigger);
    }

    public void setDatabase(Database d) {
        db = d;
    }

    public void setKeyboard(Scanner k) {
        scan = k;
    }

    public void setUsername(String user){
        username = user;
    }

    public void setPassword(String pass){
        password = pass;
    }

    public String toString() {
        return trigger + ": " + triggerText;
    }


    public abstract void execute();
}
