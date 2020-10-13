package com.email.Controller;

import com.email.Controller.Services.MessageRendererService;
import com.email.EmailManager;
import com.email.Model.EmailMessage;
import com.email.Model.SizeInteger;
import com.email.View.ViewFactory;
import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmailDetailsController extends BaseController implements Initializable {

    @FXML
    private WebView webView;

    @FXML
    private Label subjectLabel;

    @FXML
    private Label senderLabel;

    @FXML
    private HBox hBoxDownloads;

    private String Location_of_Downloads = System.getProperty("user.home") + "/Downloads/";

    private int window;


    public EmailDetailsController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EmailMessage emailMessage = emailManager.getSelectedMessage();
        subjectLabel.setText(emailMessage.getSubject());
        senderLabel.setText(emailMessage.getSender());

        try {
            loadAttachments(emailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        MessageRendererService messageRendererService = new MessageRendererService(webView.getEngine());
        messageRendererService.setEmailMessage(emailMessage);
        messageRendererService.restart();

        messageRendererService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                Stage stage = (Stage) senderLabel.getScene().getWindow();
                stage.setTitle(emailMessage.getSubject() + " : " + emailMessage.getDate().toString());
                messageRendererService.displayMessage();
            }
        });

    }

    private void loadAttachments(EmailMessage emailMessage) throws MessagingException {
        if(emailMessage.hasAttachments()){
            for (MimeBodyPart mimeBodyPart: emailMessage.getAttachmentList()){
                try {
                    AttachmentButton button = new AttachmentButton(mimeBodyPart);
                    hBoxDownloads.getChildren().add(button);
                }catch (Exception e){ System.out.println(e.getMessage());}
            }
        } else {
            return;
        }
    }

    private class AttachmentButton extends Button{
        private MimeBodyPart mimeBodyPart;
        private String dowloadedFilePath;


        public AttachmentButton (MimeBodyPart mimeBodyPart) throws MessagingException {
            this.mimeBodyPart = mimeBodyPart;
            this.setText(mimeBodyPart.getFileName() + " \n " + new SizeInteger(mimeBodyPart.getSize()).toString());
            this.dowloadedFilePath = Location_of_Downloads;
            this.setOnAction(event -> {
                DirectoryChooser directoryChooser = new DirectoryChooser();
                File saveFile = directoryChooser.showDialog(null);
                try {
                    dowloadedFilePath = saveFile.getPath() + File.separator +  mimeBodyPart.getFileName();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
                this.setText("Downloading..");
                downloadAttachment();
            });
        }

        private void downloadAttachment(){
            Service<Void> service = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    try {
                        mimeBodyPart.saveFile(dowloadedFilePath);
                        return null;
                    } catch (IOException | MessagingException e) {
                        System.out.println(e.getMessage());
                    }
                    return null;
                }
            };
            service.restart();

        }

    }

}
