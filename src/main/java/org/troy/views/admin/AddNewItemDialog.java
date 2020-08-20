package org.troy.views.admin;

import org.troy.database.daoimpl.ItemDaoImpl;
import org.troy.database.entity.Items;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class AddNewItemDialog extends JDialog {

    private FoodMenuAdminDialog loginDialog;

    private ItemDaoImpl itemDAO;

    private final JPanel contentPanel = new JPanel();
    private JTextField productNameTextField;
    private JTextField priceTextField;
    private JTextField iurlTextField;
    private JLabel lblPassword;
    private JPasswordField passwordField;
    private JPasswordField cnfPasswordField;
    private JComboBox permission;
    private JButton btnCreateAccount;

    public AddNewItemDialog(FoodMenuAdminDialog dialog, ItemDaoImpl itemDAO){
        this();
        this.itemDAO = itemDAO;

        //It"ll be used to make the dialog visible again
        this.loginDialog = dialog;
    }

    public AddNewItemDialog() {
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

        setTitle("Cafeteria System - Add new item");
        setBounds(100, 100, 360, 200);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblProductName = new JLabel("Prodcut Name");
        lblProductName.setHorizontalAlignment(SwingConstants.CENTER);
        lblProductName.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblProductName.setBounds(21, 11, 90, 14);
        contentPanel.add(lblProductName);


        productNameTextField = new JTextField();
        productNameTextField.setBounds(132, 9, 190, 20);
        contentPanel.add(productNameTextField);
        productNameTextField.setColumns(10);


        JLabel lblPrice = new JLabel("Price");
        lblPrice.setHorizontalAlignment(SwingConstants.CENTER);
        lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblPrice.setBounds(21, 42, 70, 14);
        contentPanel.add(lblPrice);


        priceTextField = new JTextField();
        priceTextField.setColumns(10);
        priceTextField.setBounds(132, 40, 190, 20);
        contentPanel.add(priceTextField);


        JLabel lblImageURL = new JLabel("Image URL");
        lblImageURL.setHorizontalAlignment(SwingConstants.CENTER);
        lblImageURL.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblImageURL.setBounds(21, 73, 70, 14);
        contentPanel.add(lblImageURL);


        iurlTextField = new JTextField();
        iurlTextField.setColumns(10);
        iurlTextField.setBounds(132, 71, 190, 20);
        contentPanel.add(iurlTextField);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
                loginDialog.setVisible(true);
            }
        });
        btnBack.setFont(new Font("Lucida Fax", Font.BOLD, 13));
        btnBack.setBounds(51, 116, 100, 23);
        contentPanel.add(btnBack);

        btnCreateAccount = new JButton("Add Item");
        btnCreateAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                addItem();
            }
        });
        btnCreateAccount.setFont(new Font("Lucida Fax", Font.BOLD, 13));
        btnCreateAccount.setBounds(193, 116, 100, 23);
        contentPanel.add(btnCreateAccount);

        //Adding window event to handle the operations performed as the signUo Dialog is closed
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){

                //display the login Dialog.
                loginDialog.setVisible(true);
            }
        });
    }

    private void addItem(){
        String productName = productNameTextField.getText();
        String str_price = priceTextField.getText();
        String iurl = iurlTextField.getText();
        int price = Integer.parseInt(str_price);

        Items item = new Items(productName, price, iurl);

        try {
            itemDAO.addNewItem(item);
            JOptionPane.showMessageDialog(loginDialog, "Add new successfully",
                    "Success!", JOptionPane.INFORMATION_MESSAGE);
            setVisible(false);
            loginDialog.setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(AddNewItemDialog.this,
                    "Error adding item" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
}
