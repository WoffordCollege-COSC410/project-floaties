package edu.wofford.wocoin.main;

//<<<<<<< HEAD
import java.util.Scanner;

import edu.wofford.wocoin.Database;
import edu.wofford.wocoin.Utilities;

public class Feature01Main {

    public static void main(String[] args) {
        Database d = new Database();
        Scanner scan = new Scanner(System.in);

        //prompt for 1st menu
        System.out.println("1: exit" + '\n' +
                "2: administrator" + '\n' +
                "Choose one: ");
        String selection1 = scan.nextLine();

        while (!selection1.equals("1") && !selection1.equals("2")) {
            System.out.println("Invalid input, enter 1 or 2: ");
            selection1 = scan.nextLine();
        }

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

                while (!selection2.equals("1") && !selection2.equals("2")) {
                    System.out.println("Invalid input, enter 1 or 2: ");
                    selection2 = scan.nextLine();
                }

                if (selection2.equals("1")) {
                    //back to menu 1
                    System.out.println("You chose 1");
                } else {
                    //add user
                    System.out.println("Add user");
                }
            }

        }
    }
}
//=======
//>>>>>>> origin/master
