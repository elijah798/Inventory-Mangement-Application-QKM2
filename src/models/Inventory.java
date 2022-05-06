package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private ObservableList<Part> allParts =
            FXCollections.observableArrayList();
    private ObservableList<Product> allProducts =
            FXCollections.observableArrayList();



    public void addPart(Part newPart){
        allParts.add(newPart);
    }
    public void addProduct(Product newProduct){
        this.allProducts.add(newProduct);
    }

    public Part lookupPart(int partId){
        Part foundPart = null;
        for (int i = 0; i < allParts.size(); i++){
            if(allParts.get(i).getId()==partId){
                foundPart =  allParts.get(i);
            }
        }
        return foundPart;
    }
    public Product lookupProduct(int productId){
        Product foundProduct = null;
        for (int i = 0; i < allProducts.size(); i++){
            if(allProducts.get(i).getId()==productId){
                foundProduct =  allProducts.get(i);
            }
        }
        return foundProduct;
    }

    public Part lookupPart(String partName){
        Part foundPart = null;
        for (int i = 0; i < allParts.size(); i++){
            if(allParts.get(i).getName()==partName){
                foundPart =  allParts.get(i);
            }
        }
        return foundPart;
    }
    public Product lookupProduct(String productName){
        Product foundProduct = null;
        for (int i = 0; i < allProducts.size(); i++){
            if(allProducts.get(i).getName()==productName){
                foundProduct =  allProducts.get(i);
            }
        }
        return foundProduct;
    }

    public void updatePart(int index, Part selectedPart){
        allParts.remove(allParts.get(index));
        allParts.add(selectedPart);
    }
    public void updateProduct(int index, Product selectedProduct){
        allProducts.remove(allProducts.get(index));
        allProducts.add(selectedProduct);
    }

    public ObservableList<Part> getAllParts(){
        return this.allParts;
    }
    public ObservableList<Product> getAllProducts(){
        return this.allProducts;
    }
}
