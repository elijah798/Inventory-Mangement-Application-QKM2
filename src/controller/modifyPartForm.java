package controller;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
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

    public void onButtonSave(ActionEvent actionEvent){

        InHouse oldPartID = (InHouse) inventory.lookupPart(Id.getText());
        if(outSourced.isSelected()){
            Outsourced part1 = new Outsourced(Integer.parseInt(Id.getText()),NameField.getText(),Double.parseDouble(PriceField.getText()) ,Integer.parseInt(StockField.getText()),Integer.parseInt(MaxField.getText()),Integer.parseInt(minField.getText()),MachineId.getText());
            inventory.updatePart(Integer.parseInt(Id.getText())-1,part1);
        }else{
            InHouse part1 = new InHouse(Integer.parseInt(Id.getText()),NameField.getText(),Double.parseDouble(PriceField.getText()) ,Integer.parseInt(StockField.getText()),Integer.parseInt(MaxField.getText()),Integer.parseInt(minField.getText()),Integer.parseInt(MachineId.getText()));
            inventory.updatePart(Integer.parseInt(Id.getText())-1,part1);
        }

        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();
    }
}
