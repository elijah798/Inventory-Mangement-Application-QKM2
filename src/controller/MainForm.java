package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainForm implements Initializable{

    public static Inventory inventory = new Inventory();
    public TableColumn<Part, Integer> tablePartId;
    public TableColumn<Part, String> tablePartName;
    public TableColumn<Part, Integer> tablePartStock;
    public TableColumn<Part, Double> tablePartPrice;
    public TableView<Part> partTable;
    public TextField PartSearchBar;
    public TableView<Product> productTable;
    public TableColumn tableProductId;
    public TableColumn tableProductName;
    public TableColumn tableProductStock;
    public TableColumn tableProductPrice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        InHouse part1 = new InHouse(1,"part1",2.0,3,1,4,1);
        InHouse part2 = new InHouse(2,"part2",1.0,1,3,9,2);
        InHouse part3 = new InHouse(3,"part3",1.5,6,0,1,3);

        Product Product1 = new Product(1,"Company 1", 200.0, 1, 1,5);
        Product Product2 = new Product(2,"Company 2", 20.0, 2, 2,12);
        Product Product3 = new Product(3,"Company 3", 2.0, 3, 5,50);

        inventory.addPart(part1);
        inventory.addPart(part2);
        inventory.addPart(part3);

        inventory.addProduct(Product1);
        inventory.addProduct(Product2);
        inventory.addProduct(Product3);


        System.out.println("I am Initialized");
        tablePartId.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        tablePartName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        tablePartPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        tablePartStock.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));

        tableProductId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        tableProductName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        tableProductPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        tableProductStock.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));

        productTable.setItems(inventory.getAllProducts());
        partTable.setItems(inventory.getAllParts());



    }

    @FXML
    private void onButtonExit(ActionEvent Event){
        javafx.application.Platform.exit();
    }
    @FXML
    private void onButtonPartForm(ActionEvent Event) throws IOException {
        Stage partFormWindow = new Stage();
        partFormWindow.setTitle("Add Part");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddPartForm.fxml"));
        partFormWindow.setScene(new Scene(loader.load()));
        partFormWindow.show();
    }

    public void onButtonModifyPart(ActionEvent actionEvent) throws IOException {
        System.out.println("Modify Part Opened");
        Stage ModifypartFormWindow = new Stage();
        ModifypartFormWindow.setTitle("Modify Part");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyPartForm.fxml"));
        ModifypartFormWindow.setScene(new Scene(loader.load()));
        modifyPartForm ModifyController = loader.<modifyPartForm>getController();
        if(partTable.getSelectionModel().getSelectedItem().getClass() == InHouse.class){
            ModifyController.setFields((InHouse) partTable.getSelectionModel().getSelectedItem());
        }else{
            ModifyController.setFields((Outsourced) partTable.getSelectionModel().getSelectedItem());
        }

        ModifypartFormWindow.show();
    }

    public void onButtonDeletePart(ActionEvent actionEvent) {

        if(!inventory.getAllParts().isEmpty()){

            partTable.getItems().set(partTable.getSelectionModel().getSelectedItem().getId()-1,null);
            partTable.setItems(inventory.getAllParts());

            System.out.println("Table refreshed");
        }else{
            System.out.println("Table is Empty");
        }


    }


    public void onPartSearchEntry(ActionEvent actionEvent) {
        ObservableList<Part> searchResults = FXCollections.observableArrayList();


        partTable.setItems(null);
        System.out.println("Table cleared");

        //test for valid entry
        if(PartSearchBar.getText().isEmpty()){

                partTable.setItems(null);
                partTable.setItems(inventory.getAllParts());
                partTable.refresh();

        } else if(PartSearchBar.getText().matches("[0-9]+") && inventory.lookupPart(Integer.parseInt(PartSearchBar.getText())) != null){

            Part searchItem = inventory.lookupPart(Integer.parseInt(PartSearchBar.getText()));
            searchResults.add(searchItem);
            inventory.lookupPart(searchItem + " Was added");
            partTable.setItems(searchResults);
            partTable.refresh();
            System.out.println(inventory.lookupPart(searchItem + " Was added"));

        }


    }

    public void onButtonProductForm(ActionEvent actionEvent) throws IOException {
        Stage partFormWindow = new Stage();
        partFormWindow.setTitle("Add Product");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddProductForm.fxml"));


        partFormWindow.setScene(new Scene(loader.load()));
        AddProductForm controller = loader.getController();

        controller.setPartList(inventory.getAllParts());
        partFormWindow.show();
    }

    public void onButtonModifyProduct(ActionEvent actionEvent) throws IOException {
        Stage partFormWindow = new Stage();
        partFormWindow.setTitle("Modify Product");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyProductForm.fxml"));


        partFormWindow.setScene(new Scene(loader.load()));
        ModifyProductForm controller = loader.getController();

        controller.setProduct(productTable.getSelectionModel().getSelectedItem());
        controller.setPartList(inventory.getAllParts());
        partFormWindow.show();
    }

    public void onButtonProductDeletePart(ActionEvent actionEvent) {

        if(!inventory.getAllProducts().isEmpty()){

            productTable.getItems().set(productTable.getSelectionModel().getSelectedItem().getId()-1,null);
            productTable.setItems(inventory.getAllProducts());
            System.out.println("Table refreshed");

        }else{

            System.out.println("Table is Empty");

        }

    }
}
