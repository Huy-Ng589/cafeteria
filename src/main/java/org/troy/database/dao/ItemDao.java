package org.troy.database.dao;

import org.troy.database.entity.Items;
import org.troy.database.entity.Users;

import java.sql.SQLException;
import java.util.List;

public interface ItemDao {

    void updateQuantity(Items tmp, int val) throws SQLException;

    void setQuantityToNull(Items food) throws SQLException;

    int getNetAmount() throws SQLException;

    int addOrder(Users users, int netAmount) throws SQLException;

    void vacateQuantityColumn();

    boolean isEmptyQuantityColumn() throws SQLException;

    List<Items> getAllItems() throws Exception;

    List<Items> getCartItems() throws Exception;
}
