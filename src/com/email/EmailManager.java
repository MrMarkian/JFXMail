package com.email;

import com.email.Controller.EmailLoginResult;
import com.email.Controller.Services.FetchFoldersService;
import com.email.Controller.Services.FolderUpdaterService;
import com.email.Model.EmailAccount;
import com.email.Model.EmailMessage;
import com.email.Model.EmailTreeItem;
import com.email.View.IconResolver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import javax.mail.Flags;
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

    private EmailMessage selectedMessage;
    private EmailTreeItem<String> selectedFolder;

    private ObservableList<EmailAccount> emailAccountsList = FXCollections.observableArrayList();
    private IconResolver iconResolver = new IconResolver();


    public ObservableList<EmailAccount> getEmailAccounts() {
        return emailAccountsList;
    }

    public EmailMessage getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(EmailMessage selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

    public EmailTreeItem<String> getSelectedFolder() {
        return selectedFolder;
    }

    public void setSelectedFolder(EmailTreeItem<String> selectedFolder) {
        this.selectedFolder = selectedFolder;
    }

    public EmailManager(){
        folderUpdaterService = new FolderUpdaterService(folderList);
        folderUpdaterService.start();
    }


    public List<Folder> getFolderList() {
        return folderList;
    }



    public void addEmailAccount(EmailAccount emailAccount){
        emailAccountsList.add(emailAccount);
        EmailTreeItem<String> treeItem = new EmailTreeItem<String>(emailAccount.getAddress());
        treeItem.setGraphic(iconResolver.getIconForFolder(emailAccount.getAddress()));
        FetchFoldersService fetchFoldersService = new FetchFoldersService(emailAccount.getStore(),treeItem, folderList );
        fetchFoldersService.start();
        treeItem.setExpanded(true);
        foldersRoot.getChildren().add(treeItem);
    }

    public void setToRead() {
        try {
            selectedMessage.setRead(true);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN,true);
            selectedFolder.decrementMessagesCount();
        } catch (Exception e){System.out.println(e.getMessage());}
    }
    public void setToUnRead() {
        try {
            selectedMessage.setRead(false);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN,false);
            selectedFolder.incrementMessagesCount();
        } catch (Exception e){System.out.println(e.getMessage());}
    }

    public void deleteSelectedMessage() {
        try {
            selectedMessage.getMessage().setFlag(Flags.Flag.DELETED,true);
            selectedFolder.getEmailMessages().remove(selectedMessage);
        }catch (Exception e){System.out.println(e.getMessage());}
    }
}
