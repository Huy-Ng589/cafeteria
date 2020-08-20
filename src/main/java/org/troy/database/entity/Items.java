package org.troy.database.entity;

public class Items {
    private int itemId;
    private String productName;
    private int price;
    private int quantity;
    private String imageURL;

    public Items(String productName, int price, String imageURL) {
        this.productName = productName;
        this.price = price;
        this.imageURL = imageURL;
    }

    public Items(int itemId, String productName, int price, int quantity, String imageURL){
        this(productName, price, imageURL);
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString(){
        return String.format("Product [Id= %s, Name= %s, Price= %s, Quantity= %s, URL= %s]",
                itemId, productName, price, quantity, imageURL);
    }

}

