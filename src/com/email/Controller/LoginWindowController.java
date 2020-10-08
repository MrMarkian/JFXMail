package com.email.Controller;
import com.email.Controller.Services.LoginService;
import com.email.EmailManager;
import com.email.Model.EmailAccount;
import com.email.View.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ResourceBundle;

public class LoginWindowController extends BaseController implements Initializable {

    @FXML
    private TextField emailAddressField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void loginButtonPressed(ActionEvent event) throws GeneralSecurityException {

        if(fieldsAreValid()){
            EmailAccount emailAccount = new EmailAccount(emailAddressField.getText(), passwordField.getText());
            LoginService loginService = new LoginService(emailAccount, emailManager);
            loginService.start();
            loginService.setOnSucceeded(e -> {
                EmailLoginResult result = loginService.getValue();
                System.out.println(result.toString());
                switch (result){
                    case SUCCESS -> {
                        System.out.println("Login Successful!" + emailAccount);
                        viewFactory.showMainWindow();
                        Stage stage = (Stage) errorLabel.getScene().getWindow();
                        viewFactory.closeStage(stage);
                    }
                    case FAILED_BY_CREDENTIALS -> errorLabel.setText("Invalid Credentails");
                    case FAILED_BY_NETWORK -> errorLabel.setText("Unable to connect to network");
                }

            });
            }





            System.out.println("Login Button Pressed");

    }

    private boolean fieldsAreValid() {
        if(emailAddressField.getText().isEmpty()){
            errorLabel.setText("Please fill email");
            return false;
        }
        if(passwordField.getText().isEmpty()){
            errorLabel.setText("Please fill password");
            return false;
        }
        return true;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        emailAddressField.setText("markianrutkowskyj@googlemail.com");
        passwordField.setText("MagicW0rd");
    }
}


