package com.email.Model;

import com.email.View.ViewFactory;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmailMessage {

    private SimpleStringProperty subject;
    private SimpleStringProperty sender;
    private SimpleStringProperty recipient;
    private SimpleObjectProperty<SizeInteger> size;
    private SimpleObjectProperty<Date> date;
    private boolean isRead;
    private Message message;
    private List<MimeBodyPart> attachmentList = new ArrayList<MimeBodyPart>();
    private SimpleObjectProperty<Boolean> hasAttachments = new SimpleObjectProperty<Boolean>(false);

    private SimpleObjectProperty<Integer> attachmentCount = new SimpleObjectProperty<Integer>(0);



    public EmailMessage(String subject, String sender, String recipient, int size, Date date, boolean isRead, Message message){
        this.subject = new SimpleStringProperty(subject);
        this.sender = new SimpleStringProperty(sender);
        this.size = new SimpleObjectProperty<SizeInteger>(new SizeInteger(size));
        this.date = new SimpleObjectProperty<Date>(date);
        this.recipient = new SimpleStringProperty(recipient);

        this.isRead = isRead;
        this.message = message;
        this.attachmentCount.set(getAttachmentCount());

    }

    public String getSubject(){
        return this.subject.get();
    }

    public SimpleStringProperty subjectProperty() {
        return subject;
    }

    public String getSender() {
        return sender.get();
    }

    public SimpleStringProperty senderProperty() {
        return sender;
    }

    public String getRecipient() {
        return recipient.get();
    }

    public SimpleStringProperty recipientProperty() {
        return recipient;
    }


    public Date getDate() {
        return date.get();
    }

    public SimpleObjectProperty<Date> dateProperty() {
        return date;
    }

    public boolean isRead() {
        return isRead;
    }

    public Message getMessage() {
        return message;
    }

    public SizeInteger getSize() {
        return size.get();
    }

    public SimpleObjectProperty<SizeInteger> sizeProperty() {
        return size;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public void addAttachment(MimeBodyPart bodyPart) {
        hasAttachments.set(true);
        attachmentList.add(bodyPart);
    }

    public List<MimeBodyPart> getAttachmentList(){
        return attachmentList;
    }

    public boolean hasAttachments(){
     return hasAttachments.get();
    }

    public int getAttachmentCount() {
        int count = 0;

        try {
            if(message.isMimeType("multipart/mixed")){
                Object object = message.getContent();

                if (object instanceof Multipart) {
                    Multipart parts = (Multipart) object;
                    if(parts.getCount() > 1){
                        for (int i = 0; i < parts.getCount(); ++i) {
                            MimeBodyPart part = (MimeBodyPart) parts.getBodyPart(i);
                            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition()))
                                ++count;
                        }
                    }
                }
            }
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
        return count;
    }
}
