package edu.wofford.wocoin;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Abstract class that defines the subsequent child menu option classes.
 */
public abstract class MenuOption {

    protected Database db;
    protected Scanner scan;
    protected PrintStream terminal;
    protected String username;
    protected String password;
    protected String trigger;
    protected String triggerText;

    /**
     * Constructor that sets the trigger and trigger text for each menu option.
     */
    public MenuOption(){
        this.triggerText = "";
        this.trigger = "";
    }

    /**
     * Sets the trigger when called.
     * @param trigger option number in the menu
     */
    public void setTriggers(int trigger){
        this.trigger = Integer.toString(trigger);
    }

    /**
     * Sets the Sets the database when called.
     * @param d database
     */
    public void setDatabase(Database d) {
        db = d;
    }

    /**
     * Sets the scanner when called.
     * @param k scanner
     */
    public void setKeyboard(Scanner k) {
        scan = k;
    }

    /**
     * Sets the PrintStream terminal when called.
     * @param t PrintStream
     */
    public void setTerminal(PrintStream t) {terminal = t;}

    /**
     * Sets the username when called.
     * @param user username
     */
    public void setUsername(String user){
        username = user;
    }

    /**
     * Sets the password when called.
     * @param pass password
     */
    public void setPassword(String pass){
        password = pass;
    }

    /**
     * Converts the trigger and trigger text into a readable menu option
     * @return string of menu option
     */
    public String toString() {
        return trigger + ": " + triggerText;
    }

    /**
     * Creates the execute method.
     */
    public abstract void execute();
}
