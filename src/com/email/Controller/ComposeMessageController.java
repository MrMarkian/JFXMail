package com.email.Controller;

import com.email.Controller.Services.EmailSenderService;
import com.email.Controller.Services.EmailSendingResult;
import com.email.EmailManager;
import com.email.Model.EmailAccount;
import com.email.View.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ComposeMessageController extends BaseController implements Initializable {
    public ComposeMessageController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    private TextField recipientTextField;

    @FXML
    private TextField subjectTextField;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private Button sendButton;

    @FXML
    private Label errorLabel;

    @FXML
    private ChoiceBox<EmailAccount> emailAccountChoice;

    @FXML
    void SendButtonPressed() {
        EmailSenderService senderService = new EmailSenderService(
                emailAccountChoice.getValue(),
                subjectTextField.getText(),
                recipientTextField.getText(),
                htmlEditor.getHtmlText());

        senderService.start();
        senderService.setOnSucceeded(event -> {
            EmailSendingResult emailSendingResult = senderService.getValue();

            switch (emailSendingResult){
                case SUCCESS -> {
                    Stage stage = (Stage) recipientTextField.getScene().getWindow();
                    viewFactory.closeStage(stage);
                }
                case FAILED_BY_PROVIDER -> errorLabel.setText("Provider Error");
                case FAILED_BY_UNEXPECTED_ERROR -> errorLabel.setText("Unexpected Error");
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        emailAccountChoice.setItems(emailManager.getEmailAccounts());
        emailAccountChoice.setValue(emailManager.getEmailAccounts().get(0));
    }
}
