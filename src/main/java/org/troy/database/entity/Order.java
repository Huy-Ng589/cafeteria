package org.troy.database.entity;

import java.util.Date;

public class Order {
    private int orderId;
    private int userId;
    private int orderTotal;
    private Date date_time;

    public Order(int id, int cust_id, int total, Date dt){
        orderId = id;
        userId = cust_id;
        orderTotal = total;
        this.date_time = dt;
    }

    public int getOrderId(){
        return orderId;
    }
    public void setOrderId(int id){
        orderId = id;
    }

    public int getCustomerId(){
        return userId;
    }
    public void setCustomerId(int id){
        userId = id;
    }

    public int getOrderTotal(){
        return orderTotal;
    }
    public void setOrderTotal(int total){
        orderTotal = total;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate_time() {
        return date_time;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }

    public String toString(){
        return String.format("Order [id= %s, Customer id= %s, Order Total= %s]",
                orderId, userId, orderTotal);
    }

}
