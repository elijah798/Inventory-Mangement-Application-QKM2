package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.InHouse;
import models.Outsourced;
import models.Part;

import java.net.URL;
import java.util.ResourceBundle;

import static controller.MainForm.inventory;

public class addPartForm implements Initializable {


    public Label partSource;
    public ToggleGroup TG;
    public RadioButton inHouse;
    public RadioButton outSourced;
    public TextField NameField;
    public TextField StockField;
    public TextField PriceField;
    public TextField MaxField;
    public TextField minField;
    public TextField MachineId;
    public TextField IdField;


    public void onButtonCancel(ActionEvent actionEvent) {

        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();
        System.out.println("Add Part Form Closed");
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        IdField.setText(Integer.toString(inventory.getAllParts().size() + 1) );

        TG.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                if(inHouse.isSelected()){
                    partSource.setText("In-House");
                }else{
                    partSource.setText("Outsourced");
                }
            }
        });


    }

    public void onButtonSave(ActionEvent actionEvent){
        if(inHouse.isSelected()){
            InHouse inhousePart = new InHouse(inventory.getAllParts().size() + 1 ,NameField.getText(),Double.parseDouble(PriceField.getText()) ,Integer.parseInt(StockField.getText()),Integer.parseInt(MaxField.getText()),Integer.parseInt(minField.getText()),Integer.parseInt(MachineId.getText()));
            inventory.addPart(inhousePart);
            Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            stage.hide();
        }else{
            Outsourced outsourcedPart = new Outsourced(inventory.getAllParts().size() + 1 ,NameField.getText(),Double.parseDouble(PriceField.getText()) ,Integer.parseInt(StockField.getText()),Integer.parseInt(MaxField.getText()),Integer.parseInt(minField.getText()),MachineId.getText());
            inventory.addPart(outsourcedPart);
            Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            stage.hide();
        }

    }
}
