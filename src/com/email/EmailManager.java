package com.email;

import com.email.Controller.EmailLoginResult;
import com.email.Controller.Services.FetchFoldersService;
import com.email.Controller.Services.FolderUpdaterService;
import com.email.Model.EmailAccount;
import com.email.Model.EmailTreeItem;
import javafx.scene.control.TreeItem;

import javax.mail.Folder;
import java.util.ArrayList;
import java.util.List;

public class EmailManager {
    //Hold information about the program state


    private EmailTreeItem<String> foldersRoot = new EmailTreeItem<String>("");

    public TreeItem<String> getFoldersRoot() {
        return foldersRoot;
    }

    private List<Folder> folderList = new ArrayList<Folder>();

    private FolderUpdaterService folderUpdaterService;


    public EmailManager(){
        folderUpdaterService = new FolderUpdaterService(folderList);
        folderUpdaterService.start();
    }


    public List<Folder> getFolderList() {
        return folderList;
    }



    public void addEmailAccount(EmailAccount emailAccount){
        EmailTreeItem<String> treeItem = new EmailTreeItem<String>(emailAccount.getAddress());
        FetchFoldersService fetchFoldersService = new FetchFoldersService(emailAccount.getStore(),treeItem, folderList );
        fetchFoldersService.start();
        treeItem.setExpanded(true);
        foldersRoot.getChildren().add(treeItem);
    }
}
