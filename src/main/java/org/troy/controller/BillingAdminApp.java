package org.troy.controller;

import org.troy.database.daoimpl.ItemDaoImpl;
import org.troy.database.daoimpl.OrderDaoImpl;
import org.troy.database.entity.Users;
import org.troy.views.admin.FoodMenuAdminDialog;
import org.troy.views.admin.AdminLoginDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class BillingAdminApp extends JFrame {

    private AdminLoginDialog adminLoginDialog;

    private JPanel contentPane;
    private JButton btnOrderMenu;
    private JButton btnNewButton;

    private ItemDaoImpl foodDAO;
    private OrderDaoImpl orderDAO;

    private Users users;

    public BillingAdminApp(final AdminLoginDialog adminLoginDialog, OrderDaoImpl orderDAO, final ItemDaoImpl foodDAO, final Users users) {

        this.adminLoginDialog = adminLoginDialog;
        this.orderDAO= orderDAO;
        this.foodDAO = foodDAO;
        this.users = users;
        System.out.println("Logged in as "+ this.users);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent we) {
                int PromptResult = JOptionPane.showConfirmDialog(null, "Exit application ?",
                        "Confirm exit", JOptionPane.OK_CANCEL_OPTION);
                if(PromptResult== JOptionPane.OK_OPTION)
                    System.exit(0);
            }
        });

        setTitle("HuyNQ Cafeteria");
        setBounds(100, 100, 550, 380);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblWelcomeToCafe = new JLabel("WELCOME TO CAFE SYSTEM");
        lblWelcomeToCafe.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblWelcomeToCafe.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcomeToCafe.setBounds(115, 22, 335, 88);
        contentPane.add(lblWelcomeToCafe);

        btnOrderMenu = new JButton("MODIFY MENU");
        btnOrderMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                //create the FoodMenuDialog and pass current dialog reference to set Visible it later
                FoodMenuAdminDialog dialog = new FoodMenuAdminDialog(BillingAdminApp.this, foodDAO, users);

                //dissolve the current dialog.
                dispose();
                dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                dialog.setVisible(true);


            }
        });
        btnOrderMenu.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnOrderMenu.setBounds(94, 121, 161, 67);
        contentPane.add(btnOrderMenu);

        btnNewButton = new JButton("VIEW HISTORY");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                //displayOrderHistoryDialog();
            }
        });
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnNewButton.setBounds(289, 121, 161, 67);
        contentPane.add(btnNewButton);

        JButton btnLogOut = new JButton("Log Out");
        btnLogOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
                adminLoginDialog.setVisible(true);
                System.out.println("Customer logged out.");
            }
        });
        btnLogOut.setBounds(455, 0, 89, 25);
        contentPane.add(btnLogOut);

        JLabel customerLabel = new JLabel();
        customerLabel.setFont(new Font("Myriad Pro", Font.PLAIN, 14));
        customerLabel.setBounds(10, 5, 250, 14);

        String firstName = users.getFirstName();
        String lastName = users.getLastName();
        customerLabel.setText("Logged in as: " + firstName + " " + lastName);
        contentPane.add(customerLabel);
    }
}

