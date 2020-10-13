package com.email.Controller.Services;

import com.email.Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ContactsService {

    private static ObservableList<Contact> contactList = FXCollections.observableArrayList();


    public ContactsService(){

    }

    public ContactsService(ObservableList<Contact> contactList) {
        ContactsService.contactList = contactList;
    }

    public static ObservableList<Contact> getContactList() {
        return contactList;
    }

    public static void setContactList(ObservableList<Contact> contactList) {
        ContactsService.contactList = contactList;
    }
}
