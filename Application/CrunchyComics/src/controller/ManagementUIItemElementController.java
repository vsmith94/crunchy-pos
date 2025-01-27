package controller;

import broker.ItemBroker;
import broker.TypeBroker;
import domain.Item;
import domain.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import manager.ControllerManager;
import manager.DatabaseManager;
import ui.ManagementUIItemElement;

/**
 *
 * @author Vinicius Smith
 */
public class ManagementUIItemElementController implements Initializable {

    @FXML
    private TextField fieldItemName;
    @FXML
    private TextField fieldItemPrice;
    @FXML
    private TextField fieldItemUPC;
    @FXML
    private TextField fieldItemDescription;
    @FXML
    private TextField fieldItemQuantity;
    @FXML
    private Label labelItemID;
    @FXML
    private Label labelError;
    @FXML
    private ComboBox comboItemType;

    private Item item;
    private ManagementUIItemElement element;
    private ManagementController mgntController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Input check for price.
        fieldItemPrice.setOnKeyTyped(e -> {
            if (e.getCharacter().matches("[^0-9.]")) {
                e.consume();
            }
        });
        
        fieldItemUPC.setOnKeyTyped(e -> {
            if (e.getCharacter().matches("[^0-9]")) {
                e.consume();
            }
        });
    }

    public void populate() {
        //New Item creation.
        if (element == null) {
            TypeBroker typeBroker = new TypeBroker(DatabaseManager.getInstance());
            List types = typeBroker.getAllTypes();
            comboItemType.setItems(FXCollections.observableArrayList(types));
            comboItemType.setValue(types.get(0));

            ItemBroker itemBroker = new ItemBroker(DatabaseManager.getInstance(), DatabaseManager.getEntityManager());
            labelItemID.setText((itemBroker.getLastID() + 1) + "");

        } else { //Existing item edit.
            fieldItemName.setText(item.getName());
            fieldItemPrice.setText(String.format("%.2f", item.getPrice()));
            fieldItemUPC.setText(item.getUpc());
            fieldItemDescription.setText(item.getDescription());
            fieldItemQuantity.setText(item.getQuantity() + "");
            labelItemID.setText(item.getItemID() + "");

            TypeBroker typeBroker = new TypeBroker(DatabaseManager.getInstance());

            comboItemType.setItems(FXCollections.observableArrayList(typeBroker.getAllTypes()));
            comboItemType.setValue(item.getItemType().toString());
        }
    }

    public void cancelBtnClicked() {
        ControllerManager.getInstance().hidePopup();
    }

    public void confirmBtnClicked() {
        ItemBroker itemBroker = new ItemBroker(DatabaseManager.getInstance(), DatabaseManager.getEntityManager());

        //Error checking
        if (fieldItemName.getText().isEmpty()) {
            labelError.setVisible(true);
            labelError.setText("Name field can't be empty!");
            fieldItemName.requestFocus();
            return;
        }
        if (fieldItemPrice.getText().isEmpty()) {
            labelError.setVisible(true);
            labelError.setText("Price field can't be empty!");
            fieldItemPrice.requestFocus();
            return;
        }

        item.setName(fieldItemName.getText());
        item.setPrice(Float.parseFloat(fieldItemPrice.getText()));
        item.setUpc(fieldItemUPC.getText());
        item.setDescription(fieldItemDescription.getText());
        item.setQuantity(Integer.parseInt(fieldItemQuantity.getText()));
        item.setItemType(new Type(comboItemType.getValue().toString()));
        //New item creation.
        if (element == null) {
            item.setItemID(Integer.parseInt(labelItemID.getText()));
            itemBroker.insert(item);
            mgntController.populateItems();

        } else { //Existing item edit.
            element.refresh();
            itemBroker.insert(item);
        }

        ControllerManager.getInstance().hidePopup();
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setManagementUIItemElement(ManagementUIItemElement element) {
        this.element = element;
    }

    public void setManagementController(ManagementController mgntController) {
        this.mgntController = mgntController;
    }
}
