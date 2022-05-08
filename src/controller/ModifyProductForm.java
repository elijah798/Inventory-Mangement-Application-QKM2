package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.InHouse;
import models.Outsourced;
import models.Part;
import models.Product;

import static controller.MainForm.inventory;

public class ModifyProductForm {

    public Product product;
    public TextField partSearch;
    private  Product currentProduct;
    public TextField ProductId;
    public TextField ProductName;
    public TextField ProductStock;
    public TextField ProductPrice;
    public TextField ProductMax;
    public TextField ProductMin;
    public TableView asPartTable;
    public TableColumn asPartID;
    public TableColumn asPartName;
    public TableColumn asPartStock;
    public TableColumn asPartPrice;
    public TableView PartTable;
    public TableColumn tablePartId;
    public TableColumn tablePartName;
    public TableColumn tablePartStock;
    public TableColumn tablePartPrice;

    public ObservableList<Part> partList = FXCollections.observableArrayList();
    public ObservableList<Part> asPartList = FXCollections.observableArrayList();



    public void onButtonAddPart(ActionEvent actionEvent) {
        if(PartTable.getSelectionModel().getSelectedItem() != null) {
            if (PartTable.getSelectionModel().getSelectedItem().getClass() == InHouse.class) {
                product.addAssociatedPart((InHouse) PartTable.getSelectionModel().getSelectedItem());
            } else {
                product.addAssociatedPart((Outsourced) PartTable.getSelectionModel().getSelectedItem());
            }
        }



    }

    public void OnButtonRemovePart(ActionEvent actionEvent) {
        if(!inventory.getAllProducts().isEmpty() && asPartTable.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.NONE,"remove " + asPartTable.getSelectionModel().getSelectedItem().getClass() + " from associated parts?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if(alert.getResult() == ButtonType.YES) {

                this.asPartList.remove(asPartTable.getSelectionModel().getSelectedItem());

            }
        }else {
            Alert alert = new Alert(Alert.AlertType.NONE,"No Part Selected", ButtonType.CLOSE);

            alert.show();
        }
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

        if(validate()) {
            product.setName(ProductName.getText());
            product.setStock(Integer.parseInt(ProductStock.getText()));
            product.setMax(Integer.parseInt(ProductMax.getText()));
            product.setMin(Integer.parseInt(ProductMin.getText()));
            product.setPrice(Double.parseDouble(ProductPrice.getText()));

            inventory.updateProduct(inventory.getAllProducts().indexOf(currentProduct), product);


            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.hide();
        }


    }

    public void onButtonCancel(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();
        System.out.println("Add Part Form Closed");

    }
    public void setPartList(ObservableList<Part> parts){

        tablePartId.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        tablePartName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        tablePartPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        tablePartStock.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));

        this.partList = FXCollections.observableArrayList(parts);

        PartTable.setItems(partList);
        }






    public void setProduct(Product product){
        this.currentProduct = product;
        this.product = new Product(product.getId(),product.getName(),product.getPrice(), product.getStock(),product.getMin(),product.getMax());
        for(Part part: product.getAllAssociatedParts()){
            this.product.addAssociatedPart(part);
        }


        ProductId.setText(Integer.toString(product.getId()));
        ProductName.setText(product.getName());
        ProductStock.setText(Integer.toString(product.getStock()));
        ProductPrice.setText(Double.toString(product.getPrice()));
        ProductMax.setText(Integer.toString(product.getMax()));
        ProductMin.setText(Integer.toString(product.getMin()));


        asPartID.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        asPartName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        asPartPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        asPartStock.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));

        asPartList = this.product.getAllAssociatedParts();
        asPartTable.setItems(this.product.getAllAssociatedParts());
    }


    public void onPartSearch(ActionEvent actionEvent) {
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

