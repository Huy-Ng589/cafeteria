package org.troy.database.ui.orders;

import org.troy.database.entity.Food;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class FoodTableModel extends AbstractTableModel {

    public static final int OBJECT_COL = -1;
    private static final int PRODUCT_CODE_COL = 0;
    private static final int PRODUCT_NAME_COL = 1;
    private static final int PRODUCT_CATEGORY_COL = 2;
    private static final int PRICE_COL = 3;
    private static final int QUANTITY_COL = 4;

    private String columnNames [] = { "Product Code", "Product Name", "Product Category",
            "Price", "Quantity" };
    private List<Food> foodItems;

    public FoodTableModel(List<Food> foodItems){

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
        Food tempFood = foodItems.get(row);
        switch(col){
            case PRODUCT_CODE_COL:
                return tempFood.getProductCode();
            case PRODUCT_NAME_COL:
                return tempFood.getProductName();
            case PRICE_COL:
                return tempFood.getPrice();
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
