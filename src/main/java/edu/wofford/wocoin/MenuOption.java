package edu.wofford.wocoin;
import java.util.Scanner;

public abstract class MenuOption {

    protected Database db = new Database("jdbc:sqlite:src/test/resources/testdbcopy.db");
    protected Scanner scan = new Scanner(System.in);
    protected String username;
    protected String password;
    protected int trigger;

    public boolean isTriggered(String s) {

    }

    public int getTrigger() {

    }

    public String getTriggerText(){

    }

    public boolean setTrigger(String s) {

    }

    public void execute() {};
}
