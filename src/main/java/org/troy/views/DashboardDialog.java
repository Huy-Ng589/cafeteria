package org.troy.views;

import org.troy.database.daoimpl.ItemDaoImpl;
import org.troy.database.daoimpl.OrderDaoImpl;
import org.troy.database.daoimpl.UserDaoImpl;
import org.troy.views.admin.AdminLoginDialog;
import org.troy.views.users.UserLoginDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DashboardDialog extends JFrame {
    private JPanel contentPane;
    private JButton btnOrderMenu;
    private JButton btnNewButton;

    public DashboardDialog() {
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent we) {
                int PromptResult = JOptionPane.showConfirmDialog(null, "Exit application ?",
                        "Confirm exit", JOptionPane.OK_CANCEL_OPTION);
                if(PromptResult== JOptionPane.OK_OPTION)
                    System.exit(0);
            }
        });

        setTitle("Cafeteria System");
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

        btnOrderMenu = new JButton("Login as Admin");
        btnOrderMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    UserDaoImpl userDao = new UserDaoImpl();
                    ItemDaoImpl foodDAO = new ItemDaoImpl();
                    OrderDaoImpl orderDAO = new OrderDaoImpl();
                    dispose();


                    AdminLoginDialog dialog = new AdminLoginDialog(DashboardDialog.this, userDao, foodDAO, orderDAO);
                    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                    dialog.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnOrderMenu.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnOrderMenu.setBounds(74, 121, 191, 67);
        contentPane.add(btnOrderMenu);

        btnNewButton = new JButton("Login as Employee");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    UserDaoImpl userDao = new UserDaoImpl();
                    ItemDaoImpl foodDAO = new ItemDaoImpl();
                    OrderDaoImpl orderDAO = new OrderDaoImpl();
                    dispose();


                    UserLoginDialog dialog = new UserLoginDialog(DashboardDialog.this, userDao, foodDAO, orderDAO);
                    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                    dialog.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnNewButton.setBounds(289, 121, 191, 67);
        contentPane.add(btnNewButton);

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DashboardDialog dialog = new DashboardDialog();
                    dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                    dialog.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}