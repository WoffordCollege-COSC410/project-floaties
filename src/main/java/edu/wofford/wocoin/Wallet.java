package edu.wofford.wocoin;

import java.io.EOFException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.Cipher;




import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import java.lang.Exception.*;




//import oracle.security.crypto.core.*;



import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.web3j.crypto.CipherException;
//import org.apache.beam.sdk.coders.CoderException;


import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.crypto.WalletUtils;
import java.security.NoSuchAlgorithmException;
import java.security.*;
import java.lang.Exception.*;


import java.io.*;

public class Wallet extends Database {

    private String Username;
    private Web3j web3;
    public Wallet (String username) {
        super(username);
        Username = username;
        String fileName = "tmp/" + username;

        if(super.userExists(username)){
            File file = new File(fileName);
            if(file.exists()){
                file.delete();
            }
            //WalletUtils.generateNewWalletFile("PASSWORD", new File("/tmp/"+username), true);

            try {
                web3 = Web3j.build(new HttpService("https://morden.infura.io/your-token"));
                String walletFileName = WalletUtils.generateFullNewWalletFile("password", new File(fileName));
            }
            catch (NoSuchAlgorithmException e){
                System.out.println("exception");
                e.printStackTrace();
            }
            catch(SecurityException e){
                e.printStackTrace();
            }
            catch(GeneralSecurityException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            //catch (CoderException e){
              //  e.printStackTrace();
            //}
            catch(CipherException e) {
                e.printStackTrace();
            }





            //Web3j web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/
            //Credentials credentials = WalletUtils.loadCredentials("password", "/tmp/" + username);

            //How can i generate private key and address using web3j instead of creating keystore json file

            //YourSmartContract contract = YourSmartContract.deploy(<web3j>, <credentials>, GAS_PRICE, GAS_LIMIT,<param1>, ..., <paramN>).send();  // constructor params




            //Credentials credentials = WalletUtils.loadCredentials("password", "/ethereum/node0/keystore/UTC--2019-08-07T17-24-10.532680697Z--0fce4741f3f54fbffb97837b4ddaa8f769ba0f91.json");
            //YourSmartContract contract = YourSmartContract.load(
             //       "0x<address>|<ensName>", <web3j>, <credentials>, GAS_PRICE, GAS_LIMIT);









            //web3.shutdown()
        }
        else{
            System.out.println("user does not exist in the database wallet not created");
            web3 = null;
        }


        web3 = null;



        //where do we find the root wallet directory
        //we ask if the jsonfile with the same name as the username exists
        //if it does delete that wallet and continue creating this one

    }


    public boolean WalletExists(){
        File f = new File("/tmp/" + Username);
        if (f.exists()) {
            return true;
        }
        else{
            return false;
        }

    }

}