package org.troy.database.entity;

import java.util.Date;

public class Order {
    private int orderId;
    private int customerId;
    private int orderTotal;
    //private Date date_time;

    public Order(int id, int cust_id, int total){
        orderId = id;
        customerId = cust_id;
        orderTotal = total;
    }

    public int getOrderId(){
        return orderId;
    }
    public void setOrderId(int id){
        orderId = id;
    }

    public int getCustomerId(){
        return customerId;
    }
    public void setCustomerId(int id){
        customerId = id;
    }

    public int getOrderTotal(){
        return orderTotal;
    }
    public void setOrderTotal(int total){
        orderTotal = total;
    }

    public String toString(){
        return String.format("Order [id= %s, Customer id= %s, Order Total= %s]",
                orderId, customerId, orderTotal);
    }

}
