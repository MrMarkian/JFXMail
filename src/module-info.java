module JFXMail {

    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;
    requires activation;
    requires java.mail;

    opens com.email;
    opens com.email.View;
    opens com.email.Controller;

}