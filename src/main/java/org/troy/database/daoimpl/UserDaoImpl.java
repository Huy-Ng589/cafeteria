package org.troy.database.daoimpl;

import org.troy.database.dao.UserDao;
import org.troy.database.entity.Users;
import org.troy.util.PasswordUtils;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

    public List<Users> getAllUser() throws SQLException {
        List<Users> list = new ArrayList<>();
        Statement myStmt = null;
        ResultSet myRes = null;
        try{
            myStmt = myConn.createStatement();
            myRes = myStmt.executeQuery("select * from users order by user_id");

            while(myRes.next()){
                Users temp = convertRowToUser(myRes);
                list.add(temp);
            }
            return list;
        }
        finally{
            if(myRes != null)
                myRes.close();
            if(myStmt != null)
                myStmt.close();
        }
    }

    public void deleteUser(Users temp) throws SQLException {
        PreparedStatement myStmt = null;
        try{
            myStmt = myConn.prepareStatement("delete from users where user_id=?");
            myStmt.setInt(1, temp.getId());
            myStmt.executeUpdate();
        }
        finally{
            if(myStmt != null)
                myStmt.close();
        }
    }

    public void addUser(Users users) throws SQLException {
        PreparedStatement myStmt = null;
        try{

            myStmt = myConn.prepareStatement("insert into users"
                    +"(first_name, last_name, username, password, permission)"
                    + " values (?, ?, ?, ?, ?)");
            myStmt.setString(1, users.getFirstName());
            myStmt.setString(2, users.getLastName());
            myStmt.setString(3, users.getUsername());

            String encryptedPassword = PasswordUtils.encryptPassword(users.getPassword());
            myStmt.setString(4, encryptedPassword);
            myStmt.setString(5, users.getPermission());

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
        int id = myRs.getInt("user_id");
        String firstName = myRs.getString("first_name");
        String lastName = myRs.getString("last_name");
        String username = myRs.getString("username");
        String encryptedPassword = myRs.getString("password");
        String permission = myRs.getString("permission");
        Users users = new Users(id, firstName, lastName, username, encryptedPassword, permission);

        return users;
    }
}
