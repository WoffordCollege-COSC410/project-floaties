package edu.wofford.wocoin;

import org.web3j.*;
import java.io.*;

public class Wallet extends Database {

    private String Username;

    public Wallet (String username) {
        super(username);
        Username = username;
        String fileName = "tmp" + "\"" + username;

        if(super.userExists(username)){
            File file = new File(fileName);
            if(file.exists()){
                file.delete();
            }
           // Web3j web3 = Web3j.build(new HttpService("ip with port"));

        }
        else{
            System.out.println("user does not exist in the database wallet not created");
        }






        //where do we find the root wallet directory
        //we ask if the jsonfile with the same name as the username exists
        //if it does delete that wallet and continue creating this one

    }


    public boolean WalletExists(){
        return false;
    }

}