package edu.wofford.wocoin.main;

//<<<<<<< HEAD
import java.util.Scanner;

import edu.wofford.wocoin.Database;
import edu.wofford.wocoin.Utilities;

public class Feature01Main {
    /*public static String ONE = "1";
    public static String TWO = "2";

    public enum Menu {ONE, TWO}
*/


    public static void main(String[] args) {

        Database d = new Database("pathToDB");
        Scanner scan = new Scanner(System.in);

        //prompt for 1st menu


        String selection1 = scan.nextLine();
        menu1(selection1);

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
                String selection2 = scan.nextLine();
                menu2(selection2);

                while (!selection2.equals("1") && !selection2.equals("2")) {
                    System.out.println("Invalid input, enter 1 or 2: ");
                    selection2 = scan.nextLine();
                }

                if (selection2.equals("1")) {
                    String selectionNew = scan.nextLine();
                    menu1(selectionNew);

                } else {
                    //add user
                    System.out.println("Add user");
                }
            }

        }
    }
    private static void menu1(String selection) {
        System.out.println("1: exit" + '\n' +
                "2: administrator" + '\n' +
                "Choose one: ");
    }
    private static void menu2(String selection) {
        System.out.println("1: back" + '\n' +
                "2: add user" + '\n' +
                "Choose one:  ");
    }



}
//=======
//>>>>>>> origin/master
