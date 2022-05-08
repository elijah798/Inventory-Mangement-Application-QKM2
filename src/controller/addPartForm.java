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
import java.util.Random;
import java.util.ResourceBundle;

import static controller.MainForm.inventory;

/**
 * Add Part Form
 *
 *
 */
public class addPartForm implements Initializable {

    /**
     * The Id.
     */
    public int ID;
    /**
     * The Part source.
     */
    public Label partSource;
    /**
     * The ToggleGroup for the Radio Buttons.
     */
    public ToggleGroup TG;
    /**
     * The In house Radio Button.
     */
    public RadioButton inHouse;
    /**
     * The Out sourced Radio Button.
     */
    public RadioButton outSourced;
    /**
     * The Name field.
     */
    public TextField NameField;
    /**
     * The Stock field.
     */
    public TextField StockField;
    /**
     * The Price field.
     */
    public TextField PriceField;
    /**
     * The Max field.
     */
    public TextField MaxField;
    /**
     * The Min field.
     */
    public TextField minField;
    /**
     * The Machine id.
     */
    public TextField MachineId;
    /**
     * The Id field.
     */
    public TextField IdField;


    /**
     * Closes the AddPartForm without any new part being created.
     *
     *
     */
    public void onButtonCancel(ActionEvent actionEvent) {

        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();
        System.out.println("Add Part Form Closed");
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ID = getID();
        IdField.setText(Integer.toString(ID) );

        TG.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            if(inHouse.isSelected()){
                partSource.setText("MachineId");
            }else{
                partSource.setText("Company Name");
            }
        });


    }

    /**
     * Get id int.
     *
     * @return A Unique ID to be used by the part.
     */
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

    /**
     * Validate Form Inputs.
     *
     * @return A boolean that is true when all forms are correctly inputted or false when incorrectly.
     */
    public boolean validate(){


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

    /**
     * Creates new Part and adds it to the inventory.
     *
     *
     */
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
