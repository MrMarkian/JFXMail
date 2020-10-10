package com.email.Controller;

import com.email.Controller.Services.MessageRendererService;
import com.email.EmailManager;
import com.email.Model.EmailMessage;
import com.email.Model.EmailTreeItem;
import com.email.Model.SizeInteger;
import com.email.View.ViewFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {

    @FXML
    private TreeView<String> emailsTreeView;

    @FXML
    private TableView<EmailMessage> emailsTableView;

    @FXML
    private TableColumn<EmailMessage, String> senderCol;

    @FXML
    private TableColumn<EmailMessage, String> subjectCol;

    @FXML
    private TableColumn<EmailMessage, String> recipientCol;

    @FXML
    private TableColumn<EmailMessage, SizeInteger> sizeCol;

    @FXML
    private TableColumn<EmailMessage, Date> dateCol;

    @FXML
    private TableColumn<EmailMessage, Integer> AttachCol;

    @FXML
    private WebView emailWebView;
    @FXML
    private Slider fontSizeSlider;

    @FXML
    private Slider TextSizeSlider;

    @FXML
    private ProgressBar emailViewProgress;



    private MessageRendererService messageRendererService;

    private MenuItem markUnReadMenuItem = new MenuItem("Mark as Unread");
    private MenuItem deleteMessageMenuItem = new MenuItem("Delete Message");
    private MenuItem showMessageMeniItem = new MenuItem("Show Details");


    public MainWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupEmailsTreeView();
        setupEmailsTableView();
        setupFolderSelection();
        setupBoldRows();
        setupMessageRendererService();
        setupMessageSelection();
        setupContextMenus();


    }



    private void setupContextMenus() {
        //todo: expand this section .. add multiple selections
        markUnReadMenuItem.setOnAction(event -> {
            emailManager.setToUnRead();
        });
        deleteMessageMenuItem.setOnAction(event -> {
            emailManager.deleteSelectedMessage();
            emailWebView.getEngine().loadContent("");
        });
        showMessageMeniItem.setOnAction(event -> {
            viewFactory.showEmailDetailsWindow();
        });
    }

    private void setupMessageSelection() {
        emailsTableView.setOnMouseClicked(event -> {
            EmailMessage emailMessage = emailsTableView.getSelectionModel().getSelectedItem();


            if(emailMessage != null){
                emailManager.setSelectedMessage(emailMessage);
                if (!emailMessage.isRead()){
                    emailManager.setToRead();
                }
                messageRendererService.setEmailMessage(emailMessage);
                messageRendererService.restart();
            }

            if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                viewFactory.showEmailDetailsWindow();
            } else {
                emailViewProgress.setVisible(true);
                emailWebView.setVisible(false);
                emailWebView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue == Worker.State.SUCCEEDED){
                        emailViewProgress.setVisible(false);
                        emailWebView.setVisible(true);
                    }
                });

            }
        });
    }

    @FXML
    void composeMessagePressed(ActionEvent event) {
        viewFactory.showComposeMessageWindow();
    }

    private void setupMessageRendererService() {
        messageRendererService = new MessageRendererService(emailWebView.getEngine());
    }

    private void setupBoldRows() {
        emailsTableView.setRowFactory(new Callback<TableView<EmailMessage>, TableRow<EmailMessage>>() {
            @Override
            public TableRow<EmailMessage> call(TableView<EmailMessage> param) {
                return new TableRow<EmailMessage>(){
                 @Override
                    protected void updateItem(EmailMessage item, boolean empty){
                     super.updateItem(item, empty);
                     if(item != null){
                         if( item.isRead()){
                             setStyle("");
                         } else {
                             setStyle("-fx-font-weight: bold");
                         }
                     }
                 }
                };
            }
        });
    }

    private void setupFolderSelection() {

        emailsTreeView.setOnMouseClicked(e -> {
            EmailTreeItem<String> item = (EmailTreeItem<String>) emailsTreeView.getSelectionModel().getSelectedItem();
            if (item != null){
                emailManager.setSelectedFolder(item);
                emailsTableView.setItems(item.getEmailMessages());
            }
        });
    }

    private void setupEmailsTableView() {
        senderCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, String>("sender"));
        subjectCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, String>("subject"));
        recipientCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, String>("recipient"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, SizeInteger>("size"));
        dateCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, Date>("date"));
        AttachCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, Integer>("attachmentCount"));
        emailsTableView.setContextMenu(new ContextMenu(markUnReadMenuItem,deleteMessageMenuItem, showMessageMeniItem));

    }

    private void setupEmailsTreeView() {
        emailsTreeView.setRoot(emailManager.getFoldersRoot());
        emailsTreeView.setShowRoot(false);
    }

    @FXML
    void optionsAction() {
        viewFactory.showOptionsWindow();
    }

    @FXML
    void addAccountAction(ActionEvent event) {
        viewFactory.showLoginWindow();
    }

    @FXML
    void zoomViewSlider(MouseEvent event) {
        emailWebView.setZoom(fontSizeSlider.getValue());

    }

    @FXML
    void  TextSizeSliderChange (MouseEvent event){
        emailWebView.setFontScale(TextSizeSlider.getValue());
    }
}
