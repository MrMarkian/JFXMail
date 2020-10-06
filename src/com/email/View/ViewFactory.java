package com.email.View;

import com.email.Controller.BaseController;
import com.email.Controller.LoginWindowController;
import com.email.EmailManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {
    private EmailManager emailManager;

    public ViewFactory(EmailManager emailManager) {
        this.emailManager = emailManager;
    }

    public void showLoginWindow(){
        System.out.println("Show Login Window Called!");
        BaseController controller = new LoginWindowController(emailManager,this,"LoginWindow.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controller.getFXMLName()));
        fxmlLoader.setController(controller);
        Parent parent ;

        try{
            parent = fxmlLoader.load();
        } catch (IOException e){
             e.printStackTrace();
             parent = null;
        }

        if(parent != null) {
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } else System.out.println("Failed to initialise Window");
    }
}
