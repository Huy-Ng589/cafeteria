package org.troy.database.ui.users;

import org.troy.database.daoimpl.FoodDaoImpl;
import org.troy.database.daoimpl.OrderDaoImpl;
import org.troy.database.daoimpl.UserDaoImpl;
import org.troy.database.entity.Users;
import org.troy.database.ui.BillingApp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class UserLoginDialog extends JDialog {

    private FoodDaoImpl foodDAO;
    private UserDaoImpl userDao;
    private OrderDaoImpl orderDAO;

    private final JPanel contentPanel = new JPanel();
    private JLabel lblWelcomeToCafeteria;
    private JPanel credentialpanel;
    private JTextField emailTextField;
    private JPasswordField passwordField;

    public UserLoginDialog(UserDaoImpl userDao, FoodDaoImpl foodDAO, OrderDaoImpl orderDAO){
        this();
        this.userDao = userDao;
        this.foodDAO = foodDAO;
        this.orderDAO = orderDAO;

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

    }

    public UserLoginDialog() {
        //this.setResizable(false);

        setTitle("HuyNQ Cafeteria - Log In");
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));
        {
            JPanel lblpanel = new JPanel();
            FlowLayout flowLayout = (FlowLayout) lblpanel.getLayout();
            flowLayout.setVgap(10);
            contentPanel.add(lblpanel, BorderLayout.NORTH);
            {
                lblWelcomeToCafeteria = new JLabel("Welcome to  Cafeteria Billing System");
                lblWelcomeToCafeteria.setFont(new Font("Sylfaen", Font.BOLD, 16));
                lblpanel.add(lblWelcomeToCafeteria);
            }
        }
        {
            credentialpanel = new JPanel();
            contentPanel.add(credentialpanel, BorderLayout.CENTER);
            credentialpanel.setLayout(null);

            JLabel lblEmail = new JLabel("Username");
            lblEmail.setFont(new Font("Sylfaen", Font.PLAIN, 15));
            lblEmail.setBounds(10, 19, 70, 14);
            credentialpanel.add(lblEmail);

            emailTextField = new JTextField();
            emailTextField.setBounds(86, 16, 180, 20);
            credentialpanel.add(emailTextField);
            emailTextField.setColumns(30);

            JLabel lblPassword = new JLabel("Password");
            lblPassword.setFont(new Font("Sylfaen", Font.PLAIN, 15));
            lblPassword.setBounds(10, 50, 70, 14);
            credentialpanel.add(lblPassword);

            JButton btnLogIn = new JButton("Log in");
            btnLogIn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    performLogin();
                }
            });
            btnLogIn.setBounds(177, 78, 89, 23);
            credentialpanel.add(btnLogIn);

            JLabel lblNewUser = new JLabel("New user ?");
            lblNewUser.setFont(new Font("Sylfaen", Font.PLAIN, 15));
            lblNewUser.setBounds(34, 121, 80, 14);
            credentialpanel.add(lblNewUser);

            JButton btnSignUp = new JButton("Sign up");
            btnSignUp.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    UserSignUpDialog dialog = new UserSignUpDialog(UserLoginDialog.this, userDao);
                    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

                    //dissolve current dialog and create new dialog
                    dispose();
                    //setVisible(false);    can use this also but dispose() is preferred to release memory
                    dialog.setVisible(true);

                }
            });
            btnSignUp.setBounds(110, 117, 89, 23);
            credentialpanel.add(btnSignUp);

            passwordField = new JPasswordField();
            passwordField.setBounds(86, 47, 180, 20);
            credentialpanel.add(passwordField);

        }
    }

    private void performLogin(){
        String email = emailTextField.getText();
        String plainTextPassword = new String(passwordField.getPassword());

        try {
            Users customer = userDao.searchUser(email);	//if not NULL, customer records found in  database
            if(customer == null){
                JOptionPane.showMessageDialog(UserLoginDialog.this, "Customer not found", "OOPS!",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            //Authentication check
            boolean check = userDao.authenticate(plainTextPassword, customer);
            if(check){
                System.out.println("Customer authenticated");
                emailTextField.setText("");
                passwordField.setText("");
                BillingApp frame = new BillingApp(UserLoginDialog.this, orderDAO, foodDAO, customer);
                frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);;
                dispose();
                frame.setVisible(true);
            }
            else{
                JOptionPane.showMessageDialog(UserLoginDialog.this, "Invalid password!", "Invalid login",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(UserLoginDialog.this, "Error logging in: "
                    + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

