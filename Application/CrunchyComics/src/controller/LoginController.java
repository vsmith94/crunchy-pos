package controller;

import javafx.scene.input.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import utility.LoginService;
import javafx.fxml.*;
import javafx.scene.paint.Color;
import javafx.event.*;
import manager.ControllerManager;
import utility.Timer;

/**
 *
 * @author Noris. UMM I MEAN: CAPSTONE GROUP, OF COURSE, TIS NOT MY WORK BUT OUR
 * WORK.
 *
 * @Notes Please make sure to correct this code. Namely: Password as well as
 * validation.
 *
 */
public class LoginController implements Initializable {

    @FXML
    private Label errorMessage = new Label("");
    @FXML
    private PasswordField passwordFieldInput;

    @FXML
    private Button btnLogin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        passwordFieldInput.setOnKeyPressed(new javafx.event.EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyboardEnter) {
                if (keyboardEnter.getCode() == KeyCode.ENTER) {
                    loginEnterBtnClicked(new ActionEvent(btnLogin, null));
                }
            }
        });
    }

    /**
     * When this method is called by clicking "Enter" in Login (assuming correct
     * credentials inputted), it will change the Scene to Main Screen
     *
     * @param event
     */
    public void loginEnterBtnClicked(ActionEvent event) {
        try {
//            Parent mainScreenParent = FXMLLoader.load(getClass().getResource("/fxml/MainScreen.fxml"));
//            Scene main = new Scene(mainScreenParent);

            LoginService ls = new LoginService();

            if (ls.checkPassword(passwordFieldInput.getText())) {

                // This line grabs the Stage information.
//                Stage mainWindowScreen = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                mainWindowScreen.setScene(main);
//                mainWindowScreen.show();
                ControllerManager.getInstance().changeScene(ControllerManager.getInstance().getLastScene());
                ControllerManager.getInstance().restorePopup();
                Timer.createTimer();
                //Reset error message.
                errorMessage.setText("");

            } else {
                errorMessage.setText("Your password is incorrect.");
                errorMessage.setTextFill(Color.rgb(210, 39, 30));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        passwordFieldInput.clear();
    }
}
