package edu.wofford.wocoin;
import java.util.Scanner;

public abstract class MenuOption {

    protected Database db;
    protected Scanner scan;
    protected String username;
    protected String password;
    protected String trigger;
    protected String triggerText;

    public MenuOption(String trigger, String triggerText) {
        this.trigger = trigger;
        this.triggerText = triggerText;
    }

    public boolean isTriggered(String s) {

    }

    public void setDatabase(Database d) {
        db = d;
    }

    public void setKeyboard(Scanner k) {
        scan = k;
    }

    public String toString() {
        return trigger + ": " + triggerText;
    }


    public abstract void execute();
}
