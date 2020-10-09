package com.email.Controller.Services;

import com.email.Model.EmailAccount;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSenderService extends Service<EmailSendingResult> {

    private EmailAccount emailAccount;
    private String Subject;
    private String Recipient;
    private String content;

    public EmailSenderService(EmailAccount emailAccount, String subject, String recipient, String content) {
        this.emailAccount = emailAccount;
        Subject = subject;
        Recipient = recipient;
        this.content = content;
    }

    @Override
    protected Task<EmailSendingResult> createTask() {
        return new Task<EmailSendingResult>() {
            @Override
            protected EmailSendingResult call() throws Exception {
                try {
                    //Create Message
                    MimeMessage mimeMessage = new MimeMessage(emailAccount.getSession());
                    mimeMessage.setFrom(emailAccount.getAddress());
                    mimeMessage.addRecipients(Message.RecipientType.TO,Recipient);
                    mimeMessage.setSubject(Subject);

                    //Set Content
                    Multipart multipart = new MimeMultipart();
                    BodyPart messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setContent(content,"text/html");
                    multipart.addBodyPart(messageBodyPart);
                    mimeMessage.setContent(multipart);

                    //Sending Message
                    Transport transport = emailAccount.getSession().getTransport();
                    transport.connect(emailAccount.getProperties().getProperty("outgoingHost"), emailAccount.getAddress(), emailAccount.getPassword());
                    transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
                    transport.close();
                    return EmailSendingResult.SUCCESS;

                } catch (MessagingException e){
                   System.out.println(e.getMessage());
                   return EmailSendingResult.FAILED_BY_PROVIDER;
                } catch (Exception e){
                    System.out.println(e.getMessage());
                    return EmailSendingResult.FAILED_BY_UNEXPECTED_ERROR;
                }

            }
        };
    }
}
