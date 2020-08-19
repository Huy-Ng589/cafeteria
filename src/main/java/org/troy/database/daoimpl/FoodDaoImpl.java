package org.troy.database.daoimpl;

import org.troy.database.dao.FoodDao;
import org.troy.database.entity.Food;
import org.troy.database.entity.Users;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FoodDaoImpl implements FoodDao {
    private Connection myConn;

    public FoodDaoImpl() throws Exception{

        //get db properties
        Properties props = new Properties();
        props.load(new FileInputStream("src\\main\\resources\\cafe.properties"));

        String user = props.getProperty("user");
        String password = props.getProperty("password");
        String dburl = props.getProperty("dburl");

        myConn = DriverManager.getConnection(dburl, user, password);

        System.out.println("Food DAO - DB connection succesful to " + dburl);

    }

    //qantity column keep tracks of the list of items selected by the user. If not null, the corresponding
    //food item is selected by the customer.
    public void updateQuantity(Food temp, int val) throws SQLException {

        PreparedStatement myStmt = null;
        try{
            myStmt = myConn.prepareStatement("update food set quantity=? where product_code=?");
            myStmt.setInt(1, val);
            myStmt.setInt(2, temp.getProductCode());
            myStmt.executeUpdate();
        }
        finally{
            if(myStmt != null)
                myStmt.close();
        }

    }

    //will be used to delete the item from cart, i.e, set quantity to null & refresh the cart table view.
    public void setQuantityToNull(Food foodItem) throws SQLException{
        PreparedStatement myStmt = null;
        try{
            myStmt = myConn.prepareStatement("update food set quantity = null where product_code =?");
            myStmt.setInt(1, foodItem.getProductCode());
            myStmt.executeUpdate();
        }
        finally{
            if(myStmt != null)
                myStmt.close();
        }
    }

    public int getNetAmount() throws SQLException{
        Statement myStmt = null;
        ResultSet myRs = null;
        try{
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery("select sum(price*quantity) as sum from food where quantity is not null");
            myRs.next();
            int sum = myRs.getInt("sum");
            return sum;
        }
        finally{
            if(myRs != null)
                myRs.close();
            if(myStmt != null)
                myStmt.close();
        }
    }

    public int addOrder(Users users, int netAmount) throws SQLException{
        PreparedStatement myStmt = null;
        Statement myStmt1 = null;
        ResultSet myRs = null;
        try{
            myStmt = myConn.prepareStatement("insert into orders (customer_id, order_total) "
                    + "values(?, ?)");
            myStmt.setInt(1, users.getId());
            myStmt.setInt(2, netAmount);
            myStmt.executeUpdate();

            myStmt1 = myConn.createStatement();
            //get the last auto incremented value (i.e, order id).
            myRs = myStmt.executeQuery("select last_insert_id() as order_id");
            myRs.next();
            int orderId = myRs.getInt("order_id");
            System.out.println(orderId);
            return orderId;
        }
        finally{
            if(myStmt != null)
                myStmt.close();
            if(myStmt1 != null)
                myStmt1.close();
            if(myRs != null)
                myRs.close();
        }

    }

    public void vacateQuantityColumn(){

        Statement myStmt = null;
        try {
            myStmt = myConn.createStatement();
            myStmt.executeUpdate("update food set quantity= null where quantity is not null");
        }
        catch (SQLException e) {
            System.out.println("Error while vacating Quantity column.");
            e.printStackTrace();
        }
    }

    public boolean isEmptyQuantityColumn() throws SQLException{
        ResultSet myRs = null;
        Statement myStmt = null;
        try {
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery("select count(quantity) as count from food");
            myRs.next();
            int count = myRs.getInt("count");
            return count > 0 ? true : false ;
        }
        finally{
            if(myRs != null)
                myRs.close();
            if(myStmt != null)
                myStmt.close();
        }
    }

    private Food convertRowToFoodItem(ResultSet myRs) throws SQLException{
        int code = myRs.getInt("product_code");
        String name = myRs.getString("product_name");
        int price = myRs.getInt("price");
        int quantity = myRs.getInt("quantity");

        Food tempFood = new Food(code, name, price, quantity);

        return tempFood;
    }

    public List<Food> getAllFood() throws Exception{
        List<Food> list = new ArrayList<Food>();
        Statement myStmt = null;
        ResultSet myRes = null;
        try{
            myStmt = myConn.createStatement();
            myRes = myStmt.executeQuery("select * from food order by product_code");

            while(myRes.next()){
                Food temp = convertRowToFoodItem(myRes);
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

    public List<Food> getCartItems() throws Exception {
        List<Food> list = new ArrayList<Food>();
        Statement myStmt = null;
        ResultSet myRes = null;
        try{
            myStmt = myConn.createStatement();
            myRes= myStmt.executeQuery("select * from food where quantity is not null");

            while(myRes.next()){
                Food temp = convertRowToFoodItem(myRes);
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

//    public static void main(String [] args){
//        try {
//            FoodDAO foodDAO = new FoodDAO();
//            List<FoodItem> list = foodDAO.getAllFoodItems();
//            for(FoodItem temp : list){
//                System.out.println(temp);
//            }
//            int totalAmt = foodDAO.getNetAmount();
//            System.out.println(totalAmt);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
}
