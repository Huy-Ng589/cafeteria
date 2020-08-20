package org.troy.model;

import org.troy.database.entity.Items;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.table.AbstractTableModel;

public class FoodTableModel extends AbstractTableModel {

    private BufferedImage image;

    public static final int OBJECT_COL = -1;
    private static final int ITEM_ID_COL = 0;
    private static final int PRODUCT_NAME_COL = 1;
    private static final int PRICE_COL = 2;
    private static final int PRODUCT_IMAGE_COL = 3;
    private static final int QUANTITY_COL = 4;

    private String columnNames [] = { "Item ID", "Product Name",
            "Price", "Image", "Quantity" };
    private List<Items> foodItems;

    public FoodTableModel(List<Items> foodItems){

        this.foodItems = foodItems;
    }

    public int getColumnCount(){
        return columnNames.length;
    }

    public int getRowCount(){
        return foodItems.size();
    }

    public String getColumnName(int col){
        return columnNames[col];
    }

    public Object getValueAt(int row, int col){
        Items tempFood = foodItems.get(row);
//        try {
//            image = ImageIO.read(getClass().getResource(tempFood.getImageURL()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        switch(col){
            case ITEM_ID_COL:
                return tempFood.getItemId();
            case PRODUCT_NAME_COL:
                return tempFood.getProductName();
            case PRICE_COL:
                return tempFood.getPrice();
            case PRODUCT_IMAGE_COL:
                return tempFood.getImageURL();
            case QUANTITY_COL:
                return tempFood.getQuantity();
            case OBJECT_COL:
                return tempFood;
            default:
                return tempFood.getProductName();
        }
    }

    public Class getColumnClass(int col){
        return getValueAt(0, col).getClass();
    }

}
