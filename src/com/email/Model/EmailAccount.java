package com.email.Model;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.Session;
import javax.mail.Store;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class EmailAccount {

    private String address;
    private String password;
    private Properties properties;
    private Store store;

    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public EmailAccount(String address, String password) throws GeneralSecurityException {
        this.address = address;
        this.password = password;
        properties = new Properties();

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);

        //todo: Replace this with googles domain ATM is trusting whole world
        properties.put("mail.imaps.ssl.trust", "*"); // see : https://stackoverflow.com/questions/20122099/error-in-javamail-pkix-path-building-failed-unable-to-find-valid-certification
        properties.put("mail.imaps.ssl.socketFactory", sf);

        properties.put("incomingHost", "imap.mail.com");
        properties.put("mail.store.protocol", "imaps");

        properties.put("mail.transport.protocol", "smtps");
        properties.put("mail.smtps.host", "smtp.mail.com");
        properties.put("mail.smtps.auth", "true");
        properties.put("outgoingHost", "smtp.mail.com");
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return address;
    }
}
