package org.troy.database.dao;

import org.troy.database.entity.Customer;
import org.troy.database.entity.Food;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface FoodDao {

    void updateQuantity(Food tmp, int val) throws SQLException;

    void setQuantityToNull(Food food) throws SQLException;

    int getNetAmount() throws SQLException;

    int addOrder(Customer customer, int netAmount) throws SQLException;

    void vacateQuantityColumn();

    boolean isEmptyQuantityColumn() throws SQLException;

    List<Food> getAllFood() throws Exception;

    List<Food> getCartItems() throws Exception;
}
