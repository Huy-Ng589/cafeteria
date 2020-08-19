package org.troy.database.dao;

import org.troy.database.entity.Order;
import org.troy.database.entity.Users;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {

    List<Order> getOrderHistory(Users users) throws SQLException;
}
