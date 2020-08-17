package org.troy.database.ui;

import org.troy.database.daoimpl.CustomerDaoImpl;
import org.troy.database.daoimpl.FoodDaoImpl;
import org.troy.database.daoimpl.OrderDaoImpl;
import org.troy.database.entity.Customer;
import org.troy.database.ui.orders.FoodMenuDialog;
import org.troy.database.ui.orders.OrderHistoryDialog;
import org.troy.database.ui.users.CustomerLoginDiaLog;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class BillingApp extends JFrame {

    private CustomerLoginDiaLog customerLoginDialog;

    private JPanel contentPane;
    private JButton btnOrderMenu;
    private JButton btnNewButton;

    private FoodDaoImpl foodDAO;
    private OrderDaoImpl orderDAO;

    private Customer customer;

    public BillingApp(final CustomerLoginDiaLog customerLoginDialog, OrderDaoImpl orderDAO, final FoodDaoImpl foodDAO, final Customer customer) {

        this.customerLoginDialog = customerLoginDialog;
        this.orderDAO= orderDAO;
        this.foodDAO = foodDAO;
        this.customer = customer;
        System.out.println("Logged in as "+ this.customer);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent we)
            {
                int PromptResult = JOptionPane.showConfirmDialog(null, "Exit application ?",
                        "Confirm exit", JOptionPane.OK_CANCEL_OPTION);
                if(PromptResult== JOptionPane.OK_OPTION)
                {
                    System.exit(0);
                }
            }
        });

        setTitle("Hungry Hobbit Cafeteria");
        setBounds(100, 100, 550, 380);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblWelcomeToCafe = new JLabel("WELCOME TO CAFE SYSTEM");
        lblWelcomeToCafe.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblWelcomeToCafe.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcomeToCafe.setBounds(75, 22, 335, 88);
        contentPane.add(lblWelcomeToCafe);

        btnOrderMenu = new JButton("ORDER MENU");
        btnOrderMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                //create the FoodMenuDialog and pass current dialog reference to set Visible it later
                FoodMenuDialog dialog = new FoodMenuDialog(BillingApp.this, foodDAO, customer);

                //dissolve the current dialog.
                dispose();
                dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                dialog.setVisible(true);


            }
        });
        btnOrderMenu.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnOrderMenu.setBounds(44, 121, 143, 67);
        contentPane.add(btnOrderMenu);

        btnNewButton = new JButton("VIEW HISTORY");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                displayOrderHistoryDialog();
            }
        });
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnNewButton.setBounds(249, 121, 161, 67);
        contentPane.add(btnNewButton);

        JButton btnLogOut = new JButton("Log Out");
        btnLogOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
                customerLoginDialog.setVisible(true);
                System.out.println("Customer logged out.");
            }
        });
        btnLogOut.setBounds(455, 0, 89, 25);
        contentPane.add(btnLogOut);

        JLabel customerLabel = new JLabel();
        customerLabel.setFont(new Font("Myriad Pro", Font.PLAIN, 14));
        customerLabel.setBounds(10, 5, 250, 14);

        String firstName = customer.getFirstName();
        String lastName = customer.getLastName();
        customerLabel.setText("Logged in as: " + firstName + " " + lastName);
        contentPane.add(customerLabel);
    }

    private void displayOrderHistoryDialog(){
        OrderHistoryDialog dialog = new OrderHistoryDialog(BillingApp.this, orderDAO, customer);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        try {
            dialog.setTableModel();
            dialog.setVisible(true);
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(BillingApp.this, "Error retrieving Order History",
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {

                    CustomerDaoImpl customerDAO = new CustomerDaoImpl();
                    FoodDaoImpl foodDAO = new FoodDaoImpl();
                    OrderDaoImpl orderDAO = new OrderDaoImpl();

                    CustomerLoginDiaLog dialog = new CustomerLoginDiaLog(customerDAO, foodDAO, orderDAO);
                    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                    dialog.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

