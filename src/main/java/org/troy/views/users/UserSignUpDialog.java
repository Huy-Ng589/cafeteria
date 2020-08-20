package org.troy.views.users;

import org.troy.database.daoimpl.UserDaoImpl;
import org.troy.database.entity.Users;
import org.troy.views.admin.AdminLoginDialog;

import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class UserSignUpDialog extends JDialog {

    private AdminLoginDialog loginDialog;

    private UserDaoImpl userDAO;

    private final JPanel contentPanel = new JPanel();
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField userTextField;
    private JLabel lblPassword;
    private JPasswordField passwordField;
    private JPasswordField cnfPasswordField;
    private JComboBox permission;
    private JButton btnCreateAccount;

    public UserSignUpDialog(AdminLoginDialog dialog, UserDaoImpl userDAO){
        this();
        this.userDAO = userDAO;

        //It"ll be used to make the dialog visible again
        this.loginDialog = dialog;
    }

    public UserSignUpDialog() {
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

        setTitle("HuyNQ Cafeteria - Sign Up");
        setBounds(100, 100, 360, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblFirstName = new JLabel("First Name");
        lblFirstName.setHorizontalAlignment(SwingConstants.CENTER);
        lblFirstName.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblFirstName.setBounds(21, 11, 70, 14);
        contentPanel.add(lblFirstName);


        firstNameTextField = new JTextField();
        firstNameTextField.setBounds(112, 9, 190, 20);
        contentPanel.add(firstNameTextField);
        firstNameTextField.setColumns(10);


        JLabel lblLastName = new JLabel("Last Name");
        lblLastName.setHorizontalAlignment(SwingConstants.CENTER);
        lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblLastName.setBounds(21, 42, 70, 14);
        contentPanel.add(lblLastName);


        lastNameTextField = new JTextField();
        lastNameTextField.setColumns(10);
        lastNameTextField.setBounds(112, 40, 190, 20);
        contentPanel.add(lastNameTextField);


        JLabel lblEmail = new JLabel("Username");
        lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblEmail.setBounds(21, 73, 70, 14);
        contentPanel.add(lblEmail);


        userTextField = new JTextField();
        userTextField.setColumns(10);
        userTextField.setBounds(112, 71, 190, 20);
        contentPanel.add(userTextField);

        lblPassword = new JLabel("Password");
        lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblPassword.setBounds(21, 106, 70, 14);
        contentPanel.add(lblPassword);


        passwordField = new JPasswordField();
        passwordField.setBounds(112, 102, 190, 20);
        contentPanel.add(passwordField);

        JLabel lblConfirmPassword = new JLabel("Confirm Password");
        lblConfirmPassword.setHorizontalAlignment(SwingConstants.CENTER);
        lblConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblConfirmPassword.setBounds(10, 135, 100, 14);
        contentPanel.add(lblConfirmPassword);

        cnfPasswordField = new JPasswordField();
        cnfPasswordField.setBounds(112, 133, 190, 20);
        contentPanel.add(cnfPasswordField);

        JLabel lblPermission = new JLabel("Permission");
        lblPermission.setHorizontalAlignment(SwingConstants.CENTER);
        lblPermission.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblPermission.setBounds(10, 168, 100, 14);
        contentPanel.add(lblPermission);

        String permissions[] = {"Admin", "Employee"};
        permission = new JComboBox(permissions);
        permission.setBounds(112, 166, 100, 20);
        contentPanel.add(permission);

        btnCreateAccount = new JButton("Create Account");
        btnCreateAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                createUser();
            }
        });
        btnCreateAccount.setFont(new Font("Lucida Fax", Font.BOLD, 13));
        btnCreateAccount.setBounds(21, 216, 158, 32);
        contentPanel.add(btnCreateAccount);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
                loginDialog.setVisible(true);
            }
        });
        btnBack.setFont(new Font("Lucida Fax", Font.BOLD, 13));
        btnBack.setBounds(213, 216, 89, 29);
        contentPanel.add(btnBack);

        //Adding window event to handle the operations performed as the signUo Dialog is closed
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){

                //display the login Dialog.
                loginDialog.setVisible(true);
            }
        });
    }

    private void createUser(){
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String username = userTextField.getText();
        String password = new String(passwordField.getPassword());
        String cnfPassword = new String(cnfPasswordField.getPassword());
        String permiss = (String) permission.getSelectedItem();

        if (!password.equals(cnfPassword)) {
            JOptionPane.showMessageDialog(this,
                    "Passwords do not match.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Users user = new Users(firstName, lastName, username, password, permiss);

        try {
            userDAO.addUser(user);
            JOptionPane.showMessageDialog(loginDialog, "Customer created successfully",
                    "Success!", JOptionPane.INFORMATION_MESSAGE);
            setVisible(false);
            loginDialog.setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(UserSignUpDialog.this,
                    "Error creating account" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
}

