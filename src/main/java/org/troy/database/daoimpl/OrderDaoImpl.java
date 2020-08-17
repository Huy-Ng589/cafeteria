package org.troy.database.daoimpl;

import org.troy.database.dao.OrderDao;
import org.troy.database.entity.Customer;
import org.troy.database.entity.Order;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class OrderDaoImpl implements OrderDao {
    private Connection myConn;

    public OrderDaoImpl() throws Exception{
        //get db properties
        Properties props = new Properties();
        props.load(new FileInputStream("src\\main\\resources\\cafe.properties"));

        String user = props.getProperty("user");
        String password = props.getProperty("password");
        String dburl = props.getProperty("dburl");

        myConn = DriverManager.getConnection(dburl, user, password);

        System.out.println("Order DAO - DB connection succesful to " + dburl);

    }

    public List<Order> getOrderHistory(Customer customer) throws SQLException {
        List<Order> list = new ArrayList<Order>();
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        try{
            String query = "select * from orders where orders.customer_id=?";
            myStmt = myConn.prepareStatement(query);

            myStmt.setInt(1, customer.getId());
            myRs = myStmt.executeQuery();
            while(myRs.next()){
                Order temp = convertRowToOrder(myRs);
                list.add(temp);
            }
            return list;
        }
        finally{
            if(myRs != null)
                myRs.close();
            if(myStmt != null)
                myStmt.close();
        }
    }

    private Order convertRowToOrder(ResultSet myRs) throws SQLException{
        int id = myRs.getInt("id");
        int customerId = myRs.getInt("customer_id");
        int orderTotal = myRs.getInt("order_total");
        Order tempOrder = new Order(id, customerId, orderTotal);
        return tempOrder;

    }
}
