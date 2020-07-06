package client;

import intefaces.Client;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import server.Commands;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public HBox privateMessagePanel;
    @FXML
    public TextField nickRecipientTextField;
    @FXML
    public Button sendPrivateMsg;
    @FXML
    public CheckBox privateCheckBox;
    @FXML
    private TextField newMsgTextField;
    @FXML
    private HBox newMessagePanel;
    @FXML
    private Button sendNewMsg;
    @FXML
    private TextArea chatTextArea;
    @FXML
    private HBox authPanel;
    @FXML
    private TextField passTextField;
    @FXML
    private TextField loginTextField;

    private Stage stage;

    private Client client;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = new ClientImpl(this);

        Platform.runLater(() -> {
            stage = (Stage) loginTextField.getScene().getWindow();
            stage.setOnCloseRequest(event -> {
                if (client.isRun()) {
                        client.sendMessage(Commands.EXIT);
                }
            });
        });

    }




    public void sendMessage(ActionEvent actionEvent) {
        client.sendMessage(newMsgTextField.getText());
        newMsgTextField.clear();
        newMsgTextField.requestFocus();
    }

    void addNewMessage(String message) {
        chatTextArea.appendText(message + "\n");
    }

    public void Enter() {
        String login = loginTextField.getText();
        String pass = passTextField.getText();
        if(!login.isEmpty() && !pass.isEmpty()) {
            client.sendMessage(String.format("%s %s %s", Commands.CHECK_AUTH, login, pass));
            passTextField.clear();
        }
    }

    public void setAuthorized(boolean authorized){
        authPanel.setVisible(!authorized);
        authPanel.setManaged(!authorized);

        newMessagePanel.setVisible(authorized);
        newMessagePanel.setManaged(authorized);
        privateCheckBox.setVisible(authorized);
        privateCheckBox.setManaged(authorized);


    }

    public void logout() {
        client.sendMessage(Commands.EXIT);
        stage.close();

    }

    public void sendPrivateMessage(ActionEvent actionEvent) {
        if(privateCheckBox.isSelected() && !nickRecipientTextField.getText().isEmpty()) {
            String nickNameMsgRecipient = nickRecipientTextField.getText();
            client.sendPrivateMessage(newMsgTextField.getText(), nickNameMsgRecipient);
            nickRecipientTextField.clear();
            newMsgTextField.clear();
            newMsgTextField.requestFocus();
        }
    }

    public void selected() {
        if(privateCheckBox.isSelected()){
            privateMessagePanel.setVisible(true);
            privateMessagePanel.setManaged(true);
            sendNewMsg.setDisable(true);
            newMsgTextField.setOnAction(this::sendPrivateMessage);
        } else {
            privateMessagePanel.setVisible(false);
            privateMessagePanel.setManaged(false);
            sendNewMsg.setDisable(false);
            newMsgTextField.setOnAction(this::sendMessage);

        }
    }
}
