package org.troy.database.dao;

import org.troy.database.entity.Customer;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface CustomerDao {

    void addCustomer(Customer customer) throws SQLException;

    boolean authenticate(String plainTextPassword, Customer customer);

    Customer searchCustomer(String email) throws SQLException;
}
