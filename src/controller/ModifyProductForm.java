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

@SuppressWarnings("ALL")
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
            Alert alert = new Alert(Alert.AlertType.NONE,"remove from associated parts?", ButtonType.YES, ButtonType.NO);
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

        if(ProductName.getText().isEmpty() || ProductName.getText().matches("\\d+")){
            Alert alert = new Alert(Alert.AlertType.NONE,"Please enter valid Name.", ButtonType.CLOSE);

            alert.show();

            return false;
        }
        if (!ProductPrice.getText().matches("^\\d+([,.]\\d?)?$")) {
            Alert alert = new Alert(Alert.AlertType.NONE,"Please enter a valid price.", ButtonType.CLOSE);

            alert.show();

            return false;
        }
        if (!ProductStock.getText().matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.NONE,"Please enter a valid inventory amount.", ButtonType.CLOSE);

            alert.show();

            return false;
        }
        if (!ProductMax.getText().matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.NONE,"Please enter a valid Maximum Amount.", ButtonType.CLOSE);

            alert.show();

            return false;
        }
        if (!ProductMin.getText().matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.NONE,"Please enter a valid Minimum Amount.", ButtonType.CLOSE);

            alert.show();
            return false;
        }


        if (Integer.parseInt(ProductMin.getText()) > Integer.parseInt(ProductMax.getText()) || (Integer.parseInt(ProductStock.getText()) < Integer.parseInt(ProductMin.getText()) || Integer.parseInt(ProductStock.getText()) > Integer.parseInt(ProductMax.getText()))) {
            Alert alert = new Alert(Alert.AlertType.NONE,"Please check the amounts on your inventory, Max Inventory, and Minimum Inventory. Inventory should be between Maximum and Minimum amounts.", ButtonType.CLOSE);

            alert.show();

            return false;
        }

        return true;
    }

    public void onButtonSaveProduct(ActionEvent actionEvent) {

        if(validate()) {
            product.setName(ProductName.getText());
            System.out.println("Modified Product name is " + product.getName());
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

            } else if(partSearch.getText().matches("\\d+")){
                String searchTerm = partSearch.getText();
                for (Part Parts: inventory.getAllParts()){
                    if (Integer.toString(Parts.getId()).startsWith(partSearch.getText())){
                        searchResults.add(Parts);
                    }
                }
                if (searchResults.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.NONE, "No part with name/ID of " + partSearch.getText(), ButtonType.CLOSE);

                    alert.show();
                }

                PartTable.setItems(searchResults);


            } else  {
                for (Part Parts: inventory.getAllParts()){
                    if (Parts.getName().toLowerCase().startsWith(partSearch.getText().toLowerCase())){
                        searchResults.add(Parts);
                    }
                }
                if (searchResults.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.NONE, "No part with name/ID of " + partSearch.getText(), ButtonType.CLOSE);

                    alert.show();
                }
                PartTable.setItems(searchResults);

            }


        }
    }

