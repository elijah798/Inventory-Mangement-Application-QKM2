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

public class ModifyProductForm {
    public Product product;
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

    public ObservableList<Part> partList;
    public ObservableList<Part> asPartList = FXCollections.observableArrayList();

    public void onButtonAddPart(ActionEvent actionEvent) {

        if(PartTable.getSelectionModel().getSelectedItem().getClass() == InHouse.class){
            asPartList.add((InHouse) PartTable.getSelectionModel().getSelectedItem());
        }else{
            asPartList.add((Outsourced) PartTable.getSelectionModel().getSelectedItem());
        }

        this.partList.remove(PartTable.getSelectionModel().getSelectedItem());
        PartTable.refresh();
        asPartTable.setItems(asPartList);

    }

    public void OnButtonRemovePart(ActionEvent actionEvent) {
        if(asPartTable.getSelectionModel().getSelectedItem() != null){
            if(asPartTable.getSelectionModel().getSelectedItem().getClass() == InHouse.class){
                partList.add((InHouse) asPartTable.getSelectionModel().getSelectedItem());
            }else{
                partList.add((Outsourced) asPartTable.getSelectionModel().getSelectedItem());
            }

            this.asPartList.remove(asPartTable.getSelectionModel().getSelectedItem());

        }
    }

    public void onButtonSaveProduct(ActionEvent actionEvent) {

        product.setName(ProductName.getText());
        product.setStock(Integer.parseInt(ProductStock.getText()));
        product.setMax(Integer.parseInt(ProductMax.getText()));
        product.setMin(Integer.parseInt(ProductMin.getText()));
        product.setPrice(Double.parseDouble(ProductPrice.getText()));
        for (Part part : asPartList) {
            product.addAssociatedPart(part);
        }
        for (Part part : partList) {
            product.deleteAssociatedPart(part); 
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
        System.out.println(partList + "These parts are now in the List");
    }

    public void setProduct(Product product){
        this.product = product;
        ProductId.setText(Integer.toString(product.getId()));
        asPartID.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        asPartName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        asPartPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        asPartStock.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        asPartTable.setItems(this.product.getAllAssociatedParts());
    }
}
