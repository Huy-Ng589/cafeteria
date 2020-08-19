package org.troy.database.daoimpl;

import org.troy.database.dao.UserDao;
import org.troy.database.entity.Users;
import org.troy.util.PasswordUtils;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class UserDaoImpl implements UserDao {
    private Connection myConn;

    public UserDaoImpl() throws Exception{

        //get db properties
        Properties props = new Properties();
        props.load(new FileInputStream("src\\main\\resources\\cafe.properties"));

        String user = props.getProperty("user");
        String password = props.getProperty("password");
        String dburl = props.getProperty("dburl");

        myConn = DriverManager.getConnection(dburl, user, password);

        System.out.println("User DAO - DB connection succesful to " + dburl);

    }

    public void addUser(Users users) throws SQLException {
        PreparedStatement myStmt = null;
        try{

            myStmt = myConn.prepareStatement("insert into users"
                    +"(first_name, last_name, username, password)"
                    + " values (?, ?, ?, ?)");
            myStmt.setString(1, users.getFirstName());
            myStmt.setString(2, users.getLastName());
            myStmt.setString(3, users.getUsername());

            String encryptedPassword = PasswordUtils.encryptPassword(users.getPassword());
            myStmt.setString(4, encryptedPassword);

            myStmt.executeUpdate();
        }
        finally{
            if(myStmt!= null)
                myStmt.close();
        }
    }

    public boolean authenticate(String plainTextPassword, Users user){
        String encryptedPassword = user.getPassword();
        return PasswordUtils.checkPassword(plainTextPassword, encryptedPassword);
    }

    public Users searchUser(String uname) throws SQLException{
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        try{

            myStmt = myConn.prepareStatement("select * from users where username=?");
            myStmt.setString(1, uname);
            myRs = myStmt.executeQuery();

            //Statement.executeQuery() never returns null if resultset is empty.
            if(!myRs.next()){
                return null;
            }
            else{
                Users users = convertRowToUser(myRs);
                return users;
            }
        }
        finally{
            if(myRs!= null)
                myRs.close();
            if(myStmt!= null)
                myStmt.close();
        }
    }

    private Users convertRowToUser(ResultSet myRs) throws SQLException{
        int id = myRs.getInt("id");
        String firstName = myRs.getString("first_name");
        String lastName = myRs.getString("last_name");
        String username = myRs.getString("username");
        String encryptedPassword = myRs.getString("password");
        Users users = new Users(id, firstName, lastName, username, encryptedPassword);

        return users;
    }
}