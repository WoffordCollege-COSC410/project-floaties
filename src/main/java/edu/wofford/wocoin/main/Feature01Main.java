
package edu.wofford.wocoin.main;

import java.util.Scanner;

import edu.wofford.wocoin.Database;
import edu.wofford.wocoin.Utilities;

public class Feature01Main {


    public static void main(String[] args) {

        Database d = new Database(args[0]);
        Scanner scan = new Scanner(System.in);
        boolean rootContinue = true;


        while (rootContinue) {
            boolean adminContinue = true;
            System.out.println("1: exit" + '\n' +
                    "2: administrator" + '\n' +
                    "Choose one: ");
            String selection1 = scan.nextLine();

            //prompt for 1st menu
            while (adminContinue) {

                if (selection1.equals("1")) {
                    adminContinue = false;
                    rootContinue = false;
                } else {
                    System.out.println("Enter password: ");
                    String password = scan.nextLine();

                    if (d.checkIsAdmin(password)) {
                        System.out.println("1: back" + '\n' +
                                "2: add user" + '\n' +
                                "3: remove user" + '\n' +
                                "Choose one:  ");
                        String selection2 = scan.nextLine();

                        if (selection2.equals("1")) {
                            adminContinue = false;

                        } else if(selection2.equals("2")){
                            System.out.println("Username: ");
                            String username = scan.nextLine();
                            System.out.println("Password: ");
                            String userPass = scan.nextLine();
                            boolean success = d.addUser(username, userPass);

                            if (success) {
                                System.out.println(username + " was added.");
                                adminContinue = false;
                                rootContinue = false;
                            } else {
                                System.out.println(username + " already exists.");
                                adminContinue = false;
                                rootContinue = false;
                            }
                        } else {
                            System.out.println("Username: ");
                            String username = scan.nextLine();

                            if (d.removeUser(username)) {
                                System.out.println(username + " was removed.");
                                adminContinue = false;
                                rootContinue = false;
                            } else {
                                System.out.println(username + " does not exist.");
                                adminContinue = false;
                                rootContinue = false;
                            }
                        }
                    } else {
                        System.out.println("Incorrect administrator password.");
                        adminContinue = false;
                        rootContinue = false;
                    }
                }


            }
        }

    }


}
