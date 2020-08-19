package org.troy.database.dao;

import org.troy.database.entity.Users;

import java.sql.SQLException;

public interface UserDao {

    void addUser(Users customer) throws SQLException;

    boolean authenticate(String plainTextPassword, Users customer);

    Users searchUser(String email) throws SQLException;
}
