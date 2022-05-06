package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
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

import java.net.URL;
import java.util.ResourceBundle;

import static controller.MainForm.inventory;

public class AddProductForm implements Initializable {
    public TextField ProductId;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ProductId.setText(Integer.toString(inventory.getAllProducts().size() + 1) );



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
       Product newProduct = new Product(Integer.parseInt(ProductId.getText()),ProductName.getText(),Double.parseDouble(ProductPrice.getText()),Integer.parseInt(ProductStock.getText()),Integer.parseInt(ProductMin.getText()),Integer.parseInt(ProductMax.getText()));
        for (Part part : asPartList) {
            newProduct.addAssociatedPart(part);
        }
        System.out.println(newProduct.getAllAssociatedParts());
        inventory.addProduct(newProduct);
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();
    }
}
