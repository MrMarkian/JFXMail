package com.email;

import com.email.Controller.EmailLoginResult;
import com.email.Controller.Services.FetchFoldersService;
import com.email.Model.EmailAccount;
import com.email.Model.EmailTreeItem;
import javafx.scene.control.TreeItem;

public class EmailManager {
    //Hold information about the program state


    private EmailTreeItem<String> foldersRoot = new EmailTreeItem<String>("");

    public TreeItem<String> getFoldersRoot() {
        return foldersRoot;
    }

    public void addEmailAccount(EmailAccount emailAccount){
        EmailTreeItem<String> treeItem = new EmailTreeItem<String>(emailAccount.getAddress());
        FetchFoldersService fetchFoldersService = new FetchFoldersService(emailAccount.getStore(),treeItem );
        fetchFoldersService.start();
        treeItem.setExpanded(true);
        foldersRoot.getChildren().add(treeItem);
    }
}
