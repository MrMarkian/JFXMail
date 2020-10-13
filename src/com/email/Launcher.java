package com.email;

import com.email.Controller.Persistance.PersistenceAccess;
import com.email.Controller.Persistance.ValidAccount;
import com.email.Controller.Services.LoginService;
import com.email.Model.AccountType;
import com.email.Model.EmailAccount;
import com.email.View.ViewFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Launcher extends Application {

    private PersistenceAccess persistenceAccess = new PersistenceAccess();
    private EmailManager emailManager = new EmailManager();

    @Override
    public void start(Stage stage) throws Exception {

        ViewFactory viewFactory = new ViewFactory(emailManager);

       List<ValidAccount> validAccountList = persistenceAccess.loadFromPersistence();
       if(validAccountList.size() >0){
           viewFactory.showMainWindow();
           for(ValidAccount validAccount : validAccountList){
               EmailAccount emailAccount = new EmailAccount(validAccount.getAddress(), validAccount.getPassword(), validAccount.getIncomingHost(),validAccount.getOutgoingHost(),validAccount.getIncomingUseSSL(), validAccount.getOutgoingUseSSL(),validAccount.getUseCompression(), validAccount.getCompressionLevel());
               LoginService loginService = new LoginService(emailAccount, emailManager);
               loginService.start();
           }
       } else {
           viewFactory.showLoginWindow();
       }

    }



    @Override
    public void stop() throws Exception {
        List<ValidAccount> validAccountList = new ArrayList<ValidAccount>();
        for (EmailAccount emailAccount : emailManager.getEmailAccounts()) {
            validAccountList.add(new ValidAccount(emailAccount.getAddress(), emailAccount.getPassword(), emailAccount.getIncomingHost(), emailAccount.getOutgoingHost(), emailAccount.getIncomingUseSSL(),emailAccount.getOutgoingUseSSL(), AccountType.IMAP, emailAccount.getUseCompression(), emailAccount.getCompressionLevel()));

        }
        persistenceAccess.saveToPersistence(validAccountList);
    }

    public static void main(String[] args){
        launch(args);
    }
}
