package com.email.Controller.Persistance;

import com.email.Model.AccountType;

import java.io.Serializable;

public class ValidAccount implements Serializable {
    private String address;
    private String password;

    private String incomingHost;

    private String outgoingHost;

    private Boolean incomingUseSSL;
    private Boolean outgoingUseSSL;

    private AccountType accountType;

    private Boolean useCompression;
    private int compressionLevel;


    public ValidAccount(String address, String password, String incomingHost, String outgoingHost, Boolean incomingUseSSL, Boolean outgoingUseSSL, AccountType accountType, Boolean useCompression, int compressionLevel) {
        this.address = address;
        this.password = password;
        this.incomingHost = incomingHost;
        this.outgoingHost = outgoingHost;
        this.incomingUseSSL = incomingUseSSL;
        this.outgoingUseSSL = outgoingUseSSL;
        this.accountType = accountType;
        this.useCompression = useCompression;
        this.compressionLevel = compressionLevel;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
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
