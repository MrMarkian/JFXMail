package com.email.Controller.Services;

import com.email.Model.EmailMessage;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.web.WebEngine;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import java.io.IOException;

public class MessageRendererService extends Service<Void> {
    private EmailMessage emailMessage;
    private WebEngine webEngine;
    private StringBuffer buffer;

    public MessageRendererService(WebEngine webEngine) {
        this.webEngine = webEngine;
        buffer = new StringBuffer();
        this.setOnSucceeded(event -> {
            displayMessage();
        });

    }

    public void setEmailMessage(EmailMessage emailMessage){
        this.emailMessage = emailMessage;
    }

    private void loadMessage() throws MessagingException, IOException {
        buffer.setLength(0); // clear buffer
        Message message = emailMessage.getMessage();
        String contentType = message.getContentType();
        if(isSimpleType(contentType)){
            buffer.append(message.getContent().toString());
        } else if (isMultiPart(contentType)){
            Multipart multipart = (Multipart) message.getContent();
            for (int i = multipart.getCount() -1; i>=0; i--){
                BodyPart bodyPart = multipart.getBodyPart(i);
                String bodyPartContentType = bodyPart.getContentType();
                if(isSimpleType(bodyPartContentType)){
                    buffer.append(bodyPart.getContent().toString());
                } //Todo: Shouldn't this have an else case?
            }
        }

    }

    private boolean isSimpleType(String contentType){
        return contentType.contains("TEXT/HTML") ||
                contentType.contains("mixed") ||
                contentType.contains("text");
    }

    private boolean isMultiPart(String contentType){
        return contentType.contains("multipart");
    }

    private void displayMessage(){
        webEngine.loadContent(buffer.toString());
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    loadMessage();
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                return null;
            }
        };
    }
}
