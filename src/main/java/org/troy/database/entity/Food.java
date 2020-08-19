package org.troy.database.entity;

public class Food {
    private int productCode;
    private String productName;
    private int price;
    private int quantity;

    public Food(int productCode, String productName, int price, int quantity){

        this.productCode = productCode;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public int getProductCode(){
        return productCode;
    }

    public void setProductCode(int productCode){
        this.productCode = productCode;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public int getQuantity(){
        return quantity;
    }

    public String getProductName(){
        return productName;
    }

    public void setProductName(String productName){
        this.productName = productName;
    }

    public int getPrice(){
        return price;
    }

    public void setPrice(int price){
        this.price = price;
    }

    @Override
    public String toString(){
        return String.format("Product [Code= %s, Name= %s, Category= %s, Price= %s]",
                productCode, productName, price);
    }

}

