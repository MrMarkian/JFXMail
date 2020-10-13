package com.email.Model;

import javax.mail.Address;
import java.util.Objects;

public class Contact {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String domain;

    private Address address;

    public Contact()
    {

    }

    public Contact(Address address){
        this.emailAddress = address.toString();
        this.address = address;
        guessDomain();
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Contact(String firstName, String lastName, String emailAddress, String domain) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.domain = domain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(firstName, contact.firstName) &&
                Objects.equals(lastName, contact.lastName) &&
                Objects.equals(emailAddress, contact.emailAddress) &&
                Objects.equals(domain, contact.domain) &&
                Objects.equals(address, contact.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, emailAddress, domain, address);
    }

    @Override
    public String toString() {
        return emailAddress + "DOMAIN: " + domain;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    private void guessDomain(){
        if(emailAddress!= null){
            String[] result = emailAddress.split("@");
            String[] tmpDomain = result[1].split(">");
            domain = tmpDomain[0];
        }
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
