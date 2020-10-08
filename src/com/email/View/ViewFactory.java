package com.email.View;

import com.email.Controller.BaseController;
import com.email.Controller.LoginWindowController;
import com.email.Controller.MainWindowController;
import com.email.Controller.OptionsWindowController;
import com.email.EmailManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ViewFactory {
    private EmailManager emailManager;

    //View Options hanlding.

    private ColorTheme colorTheme = ColorTheme.DARK;
    private FontSize fontSize = FontSize.MEDIUM;
    private ArrayList<Stage> activeStages;

    private boolean mainWindowInitalised = false;

    public ViewFactory(EmailManager emailManager) {
        this.emailManager = emailManager;
        activeStages = new ArrayList<Stage>();
    }



    private void initialiseStage(BaseController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controller.getFXMLName()));
        fxmlLoader.setController(controller);
        Parent parent;

        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            parent = null;
        }

        if (parent != null) {
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            activeStages.add(stage);
        } else System.out.println("Failed to initialise Window");
    }

    public void updateStyles() {
        for (Stage s :activeStages) {
            Scene scene = s.getScene();
            //handle the CSS
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource(ColorTheme.getCssPath(colorTheme)).toExternalForm());
            scene.getStylesheets().add(getClass().getResource(FontSize.getCssPath(fontSize)).toExternalForm());
        }

    }

    public void showLoginWindow(){
        System.out.println("Show Login Window Called!");
        BaseController controller = new LoginWindowController(emailManager,this,"LoginWindow.fxml");
        initialiseStage(controller);
    }

    public void showOptionsWindow(){
        System.out.println("Show Options Window Called!");
        BaseController controller = new OptionsWindowController(emailManager,this,"OptionsWindow.fxml");
        initialiseStage(controller);
    }

    public void showMainWindow(){
        System.out.println("Show Main Window Called!");
        BaseController controller = new MainWindowController(emailManager,this,"MainWindow.fxml");
        initialiseStage(controller);
        mainWindowInitalised = true;
    }

    public void closeStage(Stage stageToClose){

        stageToClose.close();
        activeStages.remove(stageToClose);
    }

    public ColorTheme getColorTheme() {
        return colorTheme;
    }

    public void setColorTheme(ColorTheme colorTheme) {
        this.colorTheme = colorTheme;
    }

    public FontSize getFontSize() {
        return fontSize;
    }

    public void setFontSize(FontSize fontSize) {
        this.fontSize = fontSize;
    }


    public boolean isMainWindowInitalised() {
        return mainWindowInitalised;
    }
}
