package edu.wofford.wocoin.main;
import java.io.*;
import java.nio.file.*;

import edu.wofford.wocoin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.setLevel(ch.qos.logback.classic.Level.OFF);
        if (args.length >= 1) {
            String[] realArgs = Arrays.copyOfRange(args, 1, args.length);
            if (args[0].equals("0")) {
                Feature00Main.main(realArgs);
            } else if (args[0].equals("1")) {
                Feature01Main.main(realArgs);
            } else if (args[0].equals("2")) {
                Feature02Main.main(realArgs);
            }
            else if (args[0].equals("3")){
                System.out.println("here I am at feature 3");
                String fileName = "src/test/resources/testdb.db";
                String destName = "src/test/resources/testdbcopy.db";
                File file = new File(fileName);
                File dest = new File(destName);
                try {
                    Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                catch (IOException e){
                    System.out.println(e);
                }


                Database db = new Database(destName);

                db.CreateWallet("kara");


                //Wallet w = new Wallet("kara");
                //Wallet wa = new Wallet("robert");
                //Wallet wo = new Wallet("kara");


            }
//            } else if (args[0].equals("4")) {
//                Feature04Main.main(realArgs);
//            } else {
              //  System.out.println("Feature " + args[0] + " is not valid.");
            //}

        } else {
            System.out.println("You must specify a feature number.");
        }
    }
}