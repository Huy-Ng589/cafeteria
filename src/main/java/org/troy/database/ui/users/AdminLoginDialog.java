package org.troy.database.ui.users;

import org.troy.database.daoimpl.FoodDaoImpl;
import org.troy.database.daoimpl.OrderDaoImpl;
import org.troy.database.daoimpl.UserDaoImpl;
import org.troy.database.entity.Users;
import org.troy.database.ui.BillingAdminApp;
import org.troy.database.ui.BillingApp;
import org.troy.database.ui.DashboardDialog;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class AdminLoginDialog extends JDialog {
    private DashboardDialog frame;
    private FoodDaoImpl foodDAO;
    private UserDaoImpl userDao;
    private OrderDaoImpl orderDAO;

    private final JPanel contentPanel = new JPanel();
    private JLabel lblWelcomeToCafeteria;
    private JPanel credentialpanel;
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JButton btnBack;

    public AdminLoginDialog(final DashboardDialog frame, UserDaoImpl userDao, FoodDaoImpl foodDAO, OrderDaoImpl orderDAO){
        this();
        this.frame = frame;
        this.userDao = userDao;
        this.foodDAO = foodDAO;
        this.orderDAO = orderDAO;

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent we) {
                int PromptResult = JOptionPane.showConfirmDialog(null, "Exit application ?",
                        "Confirm exit", JOptionPane.OK_CANCEL_OPTION);
                if(PromptResult== JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });

    }

    public AdminLoginDialog() {
        //this.setResizable(false);

        setTitle("HuyNQ Cafeteria - Admin Log In");
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
                lblWelcomeToCafeteria = new JLabel("Administrator site");
                lblWelcomeToCafeteria.setFont(new Font("Sylfaen", Font.BOLD, 16));
                lblpanel.add(lblWelcomeToCafeteria);
            }
        }
        {
            credentialpanel = new JPanel();
            contentPanel.add(credentialpanel, BorderLayout.CENTER);
            credentialpanel.setLayout(null);

            JLabel lblUser = new JLabel("Username");
            lblUser.setFont(new Font("Sylfaen", Font.PLAIN, 15));
            lblUser.setBounds(70, 19, 70, 14);
            credentialpanel.add(lblUser);

            userTextField = new JTextField();
            userTextField.setBounds(156, 16, 180, 20);
            credentialpanel.add(userTextField);
            userTextField.setColumns(30);

            JLabel lblPassword = new JLabel("Password");
            lblPassword.setFont(new Font("Sylfaen", Font.PLAIN, 15));
            lblPassword.setBounds(70, 50, 70, 14);
            credentialpanel.add(lblPassword);

            JButton btnLogIn = new JButton("Log in");
            btnLogIn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    performLogin();
                }
            });

            passwordField = new JPasswordField();
            passwordField.setBounds(156, 47, 180, 20);
            credentialpanel.add(passwordField);

            btnLogIn.setBounds(247, 78, 89, 23);
            credentialpanel.add(btnLogIn);

            JLabel lblNewUser = new JLabel("Add new employee?");
            lblNewUser.setFont(new Font("Sylfaen", Font.PLAIN, 15));
            lblNewUser.setBounds(105, 125, 150, 14);
            credentialpanel.add(lblNewUser);

            JButton btnSignUp = new JButton("Sign up");
            btnSignUp.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    UserSignUpDialog dialog = new UserSignUpDialog(AdminLoginDialog.this, userDao);
                    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

                    //dissolve current dialog and create new dialog
                    dispose();
                    //setVisible(false);    can use this also but dispose() is preferred to release memory
                    dialog.setVisible(true);

                }
            });
            btnSignUp.setBounds(248, 117, 89, 23);
            credentialpanel.add(btnSignUp);

            JPanel bottombtnpanel = new JPanel();
            contentPanel.add(bottombtnpanel, BorderLayout.SOUTH);
            bottombtnpanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

            btnBack = new JButton("Back");
            btnBack.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    dispose();
                    frame.setVisible(true);
                }
            });
            bottombtnpanel.add(btnBack);

            Component horizontalStrut_1 = Box.createHorizontalStrut(180);
            bottombtnpanel.add(horizontalStrut_1);
        }
    }

    private void performLogin(){
        String username = userTextField.getText();
        String plainTextPassword = new String(passwordField.getPassword());

        try {
            Users user = userDao.searchUser(username);	//if not NULL, customer records found in  database
            if(user == null){
                JOptionPane.showMessageDialog(AdminLoginDialog.this, "User not found", "OOPS!",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            //Check permission
            String checkPermission = user.getPermission();
            if (checkPermission.equals("Admin")) {
                //Authentication check
                boolean check = userDao.authenticate(plainTextPassword, user);

                if(check){
                    System.out.println("Admin authenticated");
                    userTextField.setText("");
                    passwordField.setText("");
                    BillingAdminApp frame = new BillingAdminApp(AdminLoginDialog.this, orderDAO, foodDAO, user);
                    frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);;
                    dispose();
                    frame.setVisible(true);
                }
                else{
                    JOptionPane.showMessageDialog(AdminLoginDialog.this, "Invalid password!", "Invalid login",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(AdminLoginDialog.this, "Invalid permission!", "Invalid login",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(AdminLoginDialog.this, "Error logging in: "
                    + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

