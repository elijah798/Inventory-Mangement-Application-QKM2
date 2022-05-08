package controller;



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
public class modifyPartForm implements Initializable {

    public Part part;

    public int indexOfPart;
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
    public TextField Id;


    public void onButtonCancel(ActionEvent actionEvent) {

        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();
        System.out.println("modify Part Form Closed");
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        TG.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            if(inHouse.isSelected()){
                partSource.setText("MachineId");
            }else{
                partSource.setText("Company Name");
            }
        });


    }


    public void setFields(InHouse part){
        Id.setText(Integer.toString(part.getId()));
        NameField.setText(part.getName());
        StockField.setText(Integer.toString(part.getId()));
        PriceField.setText(Double.toString(part.getPrice()));
        MaxField.setText(Integer.toString(part.getMax()));
        minField.setText(Integer.toString(part.getMin()));
        MachineId.setText(Integer.toString(part.getMachineId()));
        inHouse.setSelected(true);
    }
    public void setFields(Outsourced part){
        Id.setText(Integer.toString(part.getId()));
        NameField.setText(part.getName());
        StockField.setText(Integer.toString(part.getId()));
        PriceField.setText(Double.toString(part.getPrice()));
        MaxField.setText(Integer.toString(part.getMax()));
        minField.setText(Integer.toString(part.getMin()));
        MachineId.setText(part.getCompanyName());
        outSourced.setSelected(true);
    }


    public void setCurrentPart(Part currentPart){
        this.indexOfPart = inventory.getAllParts().indexOf(currentPart);
    }
    public boolean validate() {


        if(NameField.getText().isEmpty() || NameField.getText().matches("\\d+")){
            Alert alert = new Alert(Alert.AlertType.NONE,"Please enter valid Name.", ButtonType.CLOSE);

            alert.show();

            return false;
        }
        if (inHouse.isSelected() && !MachineId.getText().matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.NONE,"Please enter a valid number for Machine ID", ButtonType.CLOSE);

            alert.show();

            return false;
        }
        if (outSourced.isSelected() && MachineId.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.NONE,"Please enter a valid Company Name.", ButtonType.CLOSE);

            alert.show();

            return false;
        }
        if (!PriceField.getText().matches("^\\d+([,.]\\d?)?$")) {
            Alert alert = new Alert(Alert.AlertType.NONE,"Please enter a valid price.", ButtonType.CLOSE);

            alert.show();

            return false;
        }
        if (!StockField.getText().matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.NONE,"Please enter a valid inventory amount.", ButtonType.CLOSE);

            alert.show();

            return false;
        }
        if (!MaxField.getText().matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.NONE,"Please enter a valid Maximum Amount.", ButtonType.CLOSE);

            alert.show();
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a valid Number");
            alert.show();
            return false;
        }
        if (!minField.getText().matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.NONE,"Please enter a valid Minimum Amount.", ButtonType.CLOSE);

            alert.show();
            return false;
        }


        if (Integer.parseInt(minField.getText()) > Integer.parseInt(MaxField.getText()) || (Integer.parseInt(StockField.getText()) < Integer.parseInt(minField.getText()) || Integer.parseInt(StockField.getText()) > Integer.parseInt(MaxField.getText()))) {
            Alert alert = new Alert(Alert.AlertType.NONE,"Please check the amounts on your inventory, Max Inventory, and Minimum Inventory. Inventory should be between Maximum and Minimum amounts.", ButtonType.CLOSE);

            alert.show();

            return false;
        }
        return true;
    }
    public void onButtonSave(ActionEvent actionEvent){
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        if(validate()){
            if(outSourced.isSelected()){
                Part part1 = new Outsourced(Integer.parseInt(Id.getText()),NameField.getText(),Double.parseDouble(PriceField.getText()) ,Integer.parseInt(StockField.getText()),Integer.parseInt(minField.getText()),Integer.parseInt(MaxField.getText()),MachineId.getText());
                inventory.updatePart(indexOfPart,part1);
                stage.hide();
            }else{
                Part part1 = new InHouse(Integer.parseInt(Id.getText()),NameField.getText(),Double.parseDouble(PriceField.getText()) ,Integer.parseInt(StockField.getText()),Integer.parseInt(minField.getText()),Integer.parseInt(MaxField.getText()),Integer.parseInt(MachineId.getText()));
                inventory.updatePart(indexOfPart,part1);
                stage.hide();
            }
        }

    }
}
