package com.email.Model;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.Session;
import javax.mail.Store;
import java.security.GeneralSecurityException;
import java.util.Objects;
import java.util.Properties;

public class EmailAccount {

    private String address;
    private String password;
    private Properties properties;
    private Store store;

    private String incomingHost;

    private String outgoingHost;

    private Boolean incomingUseSSL;
    private Boolean outgoingUseSSL;

    private AccountType accountType;

    private Session session;

    private Boolean useCompression;
    private int compressionLevel;

    private EmailAccountSettings emailAccountSettings;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public EmailAccount (EmailAccountSettings settings){
        emailAccountSettings = settings;
    }

    public EmailAccount(String address, String password, String incomingHost, String outgoingHost, Boolean incomingUseSSL,Boolean outgoingUseSSL, Boolean useCompression, Integer compressionLevel) throws GeneralSecurityException {

        this.address = address;
        this.password = password;
        this.incomingHost = incomingHost;
        this.outgoingHost = outgoingHost;
        this.incomingUseSSL = incomingUseSSL;
        this.outgoingUseSSL = outgoingUseSSL;
        this.useCompression = useCompression;
        this.compressionLevel = compressionLevel;
        properties = new Properties();

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);

        //todo: Replace this with googles domain ATM is trusting whole world
        properties.put("mail.imaps.ssl.trust", "*"); // see : https://stackoverflow.com/questions/20122099/error-in-javamail-pkix-path-building-failed-unable-to-find-valid-certification
        properties.put("mail.imaps.ssl.socketFactory", sf);

        properties.put("incomingHost", incomingHost);
        properties.put("mail.store.protocol", "imaps");

        properties.put("mail.smtp.host", outgoingHost); //SMTP Host
        properties.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        properties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        properties.put("mail.smtp.auth", outgoingUseSSL); //Enabling SMTP Authentication
        properties.put("mail.smtp.port", "465"); //SMTP Port

        if(this.useCompression){
            properties.put("mail.imap.compress.enable", useCompression);
            properties.put("mail.imap.compress.level", compressionLevel);
        }
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

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getIncomingHost() {
        return incomingHost;
    }

    public void setIncomingHost(String incomingHost) {
        this.incomingHost = incomingHost;
    }

    public String getOutgoingHost() {
        return outgoingHost;
    }

    public void setOutgoingHost(String outgoingHost) {
        this.outgoingHost = outgoingHost;
    }

    public Boolean getIncomingUseSSL() {
        return incomingUseSSL;
    }

    public void setIncomingUseSSL(Boolean incomingUseSSL) {
        this.incomingUseSSL = incomingUseSSL;
    }

    public Boolean getOutgoingUseSSL() {
        return outgoingUseSSL;
    }

    public void setOutgoingUseSSL(Boolean outgoingUseSSL) {
        this.outgoingUseSSL = outgoingUseSSL;
    }

    public Boolean getUseCompression() {
        return useCompression;
    }

    public void setUseCompression(Boolean useCompression) {
        this.useCompression = useCompression;
    }

    public int getCompressionLevel() {
        return compressionLevel;
    }

    public void setCompressionLevel(int compressionLevel) {
        this.compressionLevel = compressionLevel;
    }
}
