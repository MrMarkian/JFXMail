package com.email.Model;

import javax.mail.Session;
import javax.mail.Store;
import java.util.Properties;

public class EmailAccountSettings {
    private String incomingHost;
    private String outgoingHost;
    private Boolean incomingUseSSL;
    private Boolean outgoingUseSSL;
    private AccountType accountType;
    private Boolean useCompression;
    private int compressionLevel;



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
