package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.InHouse;
import models.Outsourced;
import models.Part;
import models.Product;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import static controller.MainForm.inventory;

public class AddProductForm implements Initializable {
    public TextField ProductId;
    public int ID;
    public TextField ProductName;
    public TextField ProductStock;
    public TextField ProductPrice;
    public TextField ProductMax;
    public TextField ProductMin;

    public TableView<Part> asPartTable;
    public TableColumn<Part, Integer> asPartID;
    public TableColumn<Part, String> asPartName;
    public TableColumn<Part, Integer> asPartStock;
    public TableColumn<Part, Double> asPartPrice;
    public TableView PartTable;
    public TableColumn<Part, Integer> tablePartStock;
    public TableColumn<Part, Double> tablePartPrice;
    public TableColumn<Part, String> tablePartName;
    public TableColumn<Part, Integer> tablePartId;
    public ObservableList<Part> partList;
    public ObservableList<Part> asPartList = FXCollections.observableArrayList();
    public TextField partSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ID = getID();
        ProductId.setText(Integer.toString(ID) );



        asPartID.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        asPartName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        asPartPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        asPartStock.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        asPartTable.setItems(partList);

    }


    public void onButtonCancel(ActionEvent actionEvent) {

        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();
        System.out.println("Add Product Form Closed");
    }


    public void setPartList(ObservableList<Part> parts){

        tablePartId.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        tablePartName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        tablePartPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        tablePartStock.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));

        this.partList = FXCollections.observableArrayList(parts);
        PartTable.setItems(partList);
        System.out.println(partList + "These parts are now in the List");
    }





    public void onButtonAddPart(ActionEvent actionEvent) {
        //ObservableList<Part> tempPartList;

        if(PartTable.getSelectionModel().getSelectedItem().getClass() == InHouse.class){
            asPartList.add((InHouse) PartTable.getSelectionModel().getSelectedItem());
        }else{
            asPartList.add((Outsourced) PartTable.getSelectionModel().getSelectedItem());
        }


        PartTable.refresh();
        asPartTable.setItems(asPartList);


    }

    public void OnButtonRemovePart(ActionEvent actionEvent) {
        if(!inventory.getAllProducts().isEmpty() && asPartTable.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.NONE,"remove " + asPartTable.getSelectionModel().getSelectedItem().getName() + " from associated parts?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if(alert.getResult() == ButtonType.YES) {

               this.asPartList.remove(asPartTable.getSelectionModel().getSelectedItem());

            }
        }else {
            Alert alert = new Alert(Alert.AlertType.NONE,"No Part Selected", ButtonType.CLOSE);

            alert.show();
        }






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
        if(ProductName.getText().isEmpty()){
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Please enter valid Name");
            alert.show();
            return false;
        }
        if (!ProductPrice.getText().matches("^\\d+([,.]\\d?)?$")) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Please enter Valid price.");
            alert.show();
            return false;
        }
        if (!ProductStock.getText().matches("\\d+")) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a valid Number");
            alert.show();
            return false;
        }
        if (!ProductMax.getText().matches("\\d+")) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a valid Number");
            alert.show();
            return false;
        }
        if (!ProductMin.getText().matches("\\d+")) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Please Enter a valid number.");
            alert.show();
            return false;
        }


        if (Integer.parseInt(ProductMin.getText()) > Integer.parseInt(ProductMax.getText()) || (Integer.parseInt(ProductStock.getText()) < Integer.parseInt(ProductMin.getText()) || Integer.parseInt(ProductStock.getText()) > Integer.parseInt(ProductMax.getText()))) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Please check the amounts on your stock inventory, Max Inventory, and Minimum Inventory. Inventory should be between Maximum and Minimum amounts.");
            alert.show();
            return false;
        }

        return true;
    }
    public void onButtonSaveProduct(ActionEvent actionEvent) {
        if(validate()){
       Product newProduct = new Product(ID,ProductName.getText(),Double.parseDouble(ProductPrice.getText()),Integer.parseInt(ProductStock.getText()),Integer.parseInt(ProductMin.getText()),Integer.parseInt(ProductMax.getText()));
        for (Part part : asPartList) {
            newProduct.addAssociatedPart(part);
        }
        System.out.println(newProduct.getAllAssociatedParts());
        inventory.addProduct(newProduct);
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();}
    }

    public void onSearchPart(ActionEvent actionEvent) {
        ObservableList<Part> searchResults = FXCollections.observableArrayList();


        PartTable.setItems(null);


        //test for valid entry
        if(partSearch.getText().isEmpty()){

            PartTable.setItems(null);
            PartTable.setItems(inventory.getAllParts());
            PartTable.refresh();

        } else if(partSearch.getText().matches("\\d+") && inventory.lookupPart(Integer.parseInt(partSearch.getText())) != null){
            String searchTerm = partSearch.getText();
            for (Part Parts: inventory.getAllParts()){
                if (Integer.toString(Parts.getId()).startsWith(partSearch.getText())){
                    searchResults.add(Parts);
                }
            }

            PartTable.setItems(searchResults);


        } else  {
            for (Part Parts: inventory.getAllParts()){
                if (Parts.getName().toLowerCase().startsWith(partSearch.getText().toLowerCase())){
                    searchResults.add(Parts);
                }
            }
            PartTable.setItems(searchResults);

        }


    }
}
