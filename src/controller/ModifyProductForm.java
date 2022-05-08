package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.InHouse;
import models.Outsourced;
import models.Part;
import models.Product;

import static controller.MainForm.inventory;

public class ModifyProductForm {

    public Product product;
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
        if(asPartTable.getSelectionModel().getSelectedItem() != null){
            if(asPartTable.getSelectionModel().getSelectedItem().getClass() == InHouse.class){
                product.deleteAssociatedPart((InHouse) asPartTable.getSelectionModel().getSelectedItem());
            }else{
                product.deleteAssociatedPart((Outsourced) asPartTable.getSelectionModel().getSelectedItem());
            }



        }
    }

    public void onButtonSaveProduct(ActionEvent actionEvent) {

        product.setName(ProductName.getText());
        product.setStock(Integer.parseInt(ProductStock.getText()));
        product.setMax(Integer.parseInt(ProductMax.getText()));
        product.setMin(Integer.parseInt(ProductMin.getText()));
        product.setPrice(Double.parseDouble(ProductPrice.getText()));

        inventory.updateProduct(inventory.getAllProducts().indexOf(currentProduct), product);


            Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            stage.hide();


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


}
