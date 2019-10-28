
package edu.wofford.wocoin.main;

import java.util.Scanner;

import edu.wofford.wocoin.Database;
import edu.wofford.wocoin.Utilities;

public class Feature01Main {


    public static void main(String[] args) {

        Database d = new Database("pathToDB.db");
        Scanner scan = new Scanner(System.in);
        boolean keepGoing = true;

        // add while loop
        while (keepGoing) {
            System.out.println("1: exit" + '\n' +
                    "2: administrator" + '\n' +
                    "Choose one: ");
            String selection1 = scan.nextLine();

            //prompt for 1st menu
            while (selection1.equals("1") || selection1.equals("2")) {

                if (selection1.equals("1")) {
                    System.exit(0);
                } else {
                    System.out.println("Enter password: ");
                    String password = scan.nextLine();

                    if (d.checkIsAdmin(password)) {
                        System.out.println("1: back" + '\n' +
                                "2: add user" + '\n' +
                                "Choose one:  ");
                        String selection2 = scan.nextLine();

                        if (selection2.equals("1")) {
                            break;

                        } else {
                            System.out.println("Username: ");
                            String username = scan.nextLine();
                            System.out.println("Password: ");
                            String userPass = scan.nextLine();
                            d.addUser(username, userPass);

                            if (d.addUser(username, userPass)) {
                                System.out.println(username + " was added.");
                                keepGoing = false;
                                break;
                            } else {
                                System.out.println(username + " already exists.");
                                keepGoing = false;
                                break;
                            }


                        }
                    }


                }

            }
        }

    }




}
