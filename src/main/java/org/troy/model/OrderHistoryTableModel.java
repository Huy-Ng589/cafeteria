package org.troy.model;

import org.troy.database.entity.Order;

import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class OrderHistoryTableModel extends AbstractTableModel {

    private static final int OBJECT_COL = -1;
    private static final int ORDER_ID_COL = 0;
    private static final int ORDER_TOTAL_COL = 1;
    private static final int DATE_TIME = 2;

    private String columnNames[] = {"Order Id", "Order Total", "Date"};

    private List<Order> orderHistory;

    public OrderHistoryTableModel(List<Order> list){
        orderHistory = list;
    }

    public int getRowCount(){
        return orderHistory.size();
    }

    public int getColumnCount(){
        return columnNames.length;
    }

    public String getColumnName(int col){
        return columnNames[col];
    }

    public Object getValueAt(int row, int col){
        Order temp = orderHistory.get(row);
        switch(col){
            case ORDER_ID_COL:
                return temp.getOrderId();
            case ORDER_TOTAL_COL:
                return temp.getOrderTotal();
            case DATE_TIME:
                return temp.getDate_time();
            case OBJECT_COL:
                return temp;
            default:
                return temp.getOrderId();
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Class getColumnClass(int col){
        return getValueAt(0, col).getClass();
    }
}

