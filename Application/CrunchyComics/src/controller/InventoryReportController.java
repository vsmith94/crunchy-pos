/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import broker.ItemBroker;
import domain.Item;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import manager.ControllerManager;

/**
 *
 * @author Vinicius Smith
 */
public class InventoryReportController {

    @FXML
    TextField textFieldFileName;
    @FXML
    TextField textFieldLocation;

    private File folder;

    public void selectFolderClicked() {
        DirectoryChooser dirChooser = new DirectoryChooser();
        folder = dirChooser.showDialog(ControllerManager.getInstance().getWindow());
        textFieldLocation.setText(folder.getAbsolutePath());
    }

    public void confirmBtnClicked() {
        if (textFieldFileName.getText().isEmpty()) {
            textFieldFileName.requestFocus();
            return;
        }
        if (textFieldLocation.getText().isEmpty()) {
            textFieldLocation.requestFocus();
            return;
        }

        ItemBroker itemBroker = new ItemBroker();
        writeFile(itemBroker.getAllItems());
        ControllerManager.getInstance().hidePopup();

    }

    public void cancelBtnClicked() {
        ControllerManager.getInstance().hidePopup();
    }

    private String correctFileName(String filename) {
        if (filename.endsWith(".csv")) {
            return filename;
        }
        return filename + ".csv";
    }

    private void writeFile(List<Item> items) {
        File file = new File(folder.getAbsolutePath() + "/" + correctFileName(textFieldFileName.getText()));
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("ID,Type,Name,Description,Quantity,Price,UPC\n");
            for (Item i : items) {
                String line = String.format("%d,%s,%s,%s,%d,%.2f,%s\n", i.getItemID(), i.getItemType().getItemType(), i.getName(), i.getDescription(), i.getQuantity(), i.getPrice(), i.getUpc());
                writer.write(line);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Could not save file. Error:" + e.getMessage());
        }
    }
}
