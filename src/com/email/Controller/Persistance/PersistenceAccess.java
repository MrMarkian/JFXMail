package com.email.Controller.Persistance;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenceAccess {

    private String VALID_ACCOUNT_LOCATION = System.getProperty("user.home")+ File.separator +"ValidAccounts.ser";
    private Encoder encoder = new Encoder();

    public List<ValidAccount> loadFromPersistence(){
        List<ValidAccount> resultList = new ArrayList<ValidAccount>();

        try {

            FileInputStream fileInputStream = new FileInputStream(VALID_ACCOUNT_LOCATION);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            List<ValidAccount> persistedList = (List<ValidAccount>) objectInputStream.readObject();
            decodeFields(persistedList);
            resultList.addAll(persistedList);
        } catch (Exception e){System.out.println(e.getMessage());}
        return resultList;
    }

    private void decodeFields(List<ValidAccount> persistedList) {
        for (ValidAccount validAccount: persistedList) {
            String originalUsername = validAccount.getAddress();
            String originalPassword = validAccount.getPassword();
            validAccount.setAddress(encoder.decode(originalUsername));
            validAccount.setPassword(encoder.decode(originalPassword));
        }
    }

    public void saveToPersistence(List<ValidAccount> validAccounts){
        try {
            System.out.println(VALID_ACCOUNT_LOCATION);
            File file = new File(VALID_ACCOUNT_LOCATION);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            encodeFields(validAccounts);


            objectOutputStream.writeObject(validAccounts);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e){System.out.println(e.getMessage());}

    }

    private void encodeFields(List<ValidAccount> validAccounts) {
        for (ValidAccount validAccount: validAccounts) {
            String originalUsername = validAccount.getAddress();
            String originalPassword = validAccount.getPassword();

            validAccount.setPassword(encoder.encode(originalPassword));
            validAccount.setAddress(encoder.encode(originalUsername));
        }
    }


}
