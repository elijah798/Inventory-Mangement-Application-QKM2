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
import java.util.Random;
import java.util.ResourceBundle;

import static controller.MainForm.inventory;

public class addPartForm implements Initializable {

    public int ID;
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

        ID = getID();
        IdField.setText(Integer.toString(ID) );

        TG.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                if(inHouse.isSelected()){
                    partSource.setText("MachineId");
                }else{
                    partSource.setText("Company Name");
                }
            }
        });


    }
    public int getID(){

        Random randID = new Random();
        int id = randID.nextInt() * (32412 + inventory.getAllParts().size()) + 1 & Integer.MAX_VALUE;

        if (inventory.lookupPart(id) == null){
            return id;
        }else{
            getID();
        }
        return id;
    }

    public boolean validate(){
    Alert alert = new Alert(Alert.AlertType.NONE);

        if(NameField.getText().isEmpty()){
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Please enter valid Name");
            alert.show();
            return false;
        }
        if (inHouse.isSelected() && !MachineId.getText().matches("\\d+")) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a number for machine ID");
            alert.show();
            return false;
        }
        if (outSourced.isSelected() && MachineId.getText().isEmpty()) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Please enter an appropriate Company Name");
            alert.show();
            return false;
        }
        if (!PriceField.getText().matches("^\\d+([,.]\\d?)?$")) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Please enter Valid price.");
            alert.show();
            return false;
        }
        if (!StockField.getText().matches("\\d+")) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a valid Number");
            alert.show();
            return false;
        }
        if (!MaxField.getText().matches("\\d+")) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a valid Number");
            alert.show();
            return false;
        }
        if (!minField.getText().matches("\\d+")) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Please Enter a valid number.");
            alert.show();
            return false;
        }


        if (Integer.parseInt(minField.getText()) > Integer.parseInt(MaxField.getText()) || (Integer.parseInt(StockField.getText()) < Integer.parseInt(minField.getText()) || Integer.parseInt(StockField.getText()) > Integer.parseInt(MaxField.getText()))) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Please check the amounts on your inventory, Max Inventory, and Minimum Inventory. Inventory should be between Maximum and Minimum amounts.");
            alert.show();
            return false;
        }

    return true;
    }

    public void onButtonSave(ActionEvent actionEvent){

        if(inHouse.isSelected() && validate()){
            Part inhousePart = new InHouse(ID ,NameField.getText(),Double.parseDouble(PriceField.getText()) ,Integer.parseInt(StockField.getText()),Integer.parseInt(minField.getText()),Integer.parseInt(MaxField.getText()),Integer.parseInt(MachineId.getText()));
            inventory.addPart(inhousePart);
            Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            stage.hide();

        }else if (outSourced.isSelected() && validate()){
            Part outsourcedPart = new Outsourced(ID ,NameField.getText(),Double.parseDouble(PriceField.getText()) ,Integer.parseInt(StockField.getText()),Integer.parseInt(minField.getText()),Integer.parseInt(MaxField.getText()),MachineId.getText());
            inventory.addPart(outsourcedPart);
            Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            stage.hide();
        }



    }
}
