package org.troy.database.dao;

import org.troy.database.entity.Customer;
import org.troy.database.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface OrderDao {

    List<Order> getOrderHistory(Customer customer) throws SQLException;
}
