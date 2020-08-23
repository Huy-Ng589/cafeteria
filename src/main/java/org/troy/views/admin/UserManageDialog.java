package org.troy.views.admin;

import org.troy.controller.BillingAdminApp;
import org.troy.database.daoimpl.ItemDaoImpl;
import org.troy.database.daoimpl.UserDaoImpl;
import org.troy.database.entity.Items;
import org.troy.database.entity.Order;
import org.troy.database.entity.Users;
import org.troy.model.FoodTableModel;
import org.troy.model.OrderHistoryTableModel;
import org.troy.model.UserTableModel;
import org.troy.views.orders.CheckoutDialog;
import org.troy.views.orders.FoodMenuDialog;
import org.troy.views.users.UserSignUpDialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

@SuppressWarnings("serial")
public class UserManageDialog extends JDialog {

    private BillingAdminApp frame;

    private UserDaoImpl userDao;
    private Users users;

    private final JPanel contentPanel = new JPanel();
    JScrollPane tabelscrollPane;
    private JTextField textField;

    private JTable userTable;
    private UserTableModel tableModel;

    JButton btnBack;
    JButton btnDeleteUser;
    JButton btnSignUp;


    public UserManageDialog(final BillingAdminApp frame, final UserDaoImpl userDao, final Users users) {

        this.frame = frame;
        this.userDao = userDao;
        this.users = users;

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent we)
            {
                int PromptResult = JOptionPane.showConfirmDialog(null, "Exit application ?",
                        "Confirm exit", JOptionPane.OK_CANCEL_OPTION);
                if(PromptResult == JOptionPane.OK_OPTION) {
                    //refreshUserView();
                    System.exit(0);
                }
            }
        });

        setTitle("Cafeteria System - User Table");
        setBounds(100, 100, 880, 620);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));

        JPanel centerpanel = new JPanel();
        contentPanel.add(centerpanel, BorderLayout.CENTER);
        centerpanel.setLayout(new BorderLayout(0, 0));

        JPanel addpanel = new JPanel();
        FlowLayout flowLayout = (FlowLayout) addpanel.getLayout();
        flowLayout.setVgap(10);
        flowLayout.setAlignment(FlowLayout.LEFT);
        centerpanel.add(addpanel, BorderLayout.SOUTH);


        JPanel toplabelpanel = new JPanel();
        centerpanel.add(toplabelpanel, BorderLayout.NORTH);
        toplabelpanel.setLayout(new GridLayout(3, 1, 0, 0));

        JLabel lblFoodOrderMenu = new JLabel("User Manage Table");
        lblFoodOrderMenu.setHorizontalAlignment(SwingConstants.CENTER);
        lblFoodOrderMenu.setFont(new Font("Tahoma", Font.BOLD, 15));
        toplabelpanel.add(lblFoodOrderMenu);

        Component rigidArea = Box.createRigidArea(new Dimension(20, 5));
        toplabelpanel.add(rigidArea);

        JLabel lblPleaseSelectThe = new JLabel("Modify User");
        lblPleaseSelectThe.setHorizontalAlignment(SwingConstants.CENTER);
        lblPleaseSelectThe.setFont(new Font("Tahoma", Font.BOLD, 12));
        toplabelpanel.add(lblPleaseSelectThe);

        tabelscrollPane = new JScrollPane();
        centerpanel.add(tabelscrollPane, BorderLayout.CENTER);

        userTable = new JTable();

        try{
            tableModel = new UserTableModel(userDao.getAllUser());
            userTable.setModel(tableModel);

            //tabelscrollPane.add(foodtable.getTableHeader(), BorderLayout.NORTH);
            tabelscrollPane.setViewportView(userTable);
            alignTable();

        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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

        Component horizontalStrut_1 = Box.createHorizontalStrut(290);
        bottombtnpanel.add(horizontalStrut_1);

        btnSignUp = new JButton("Add new user");
        btnSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UserSignUpDialog dialog = new UserSignUpDialog(UserManageDialog.this, userDao);
                dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

                //dissolve current dialog and create new dialog
                dispose();
                //setVisible(false);    can use this also but dispose() is preferred to release memory
                dialog.setVisible(true);
                refreshUserView();
            }
        });
        bottombtnpanel.add(btnSignUp);

        Component horizontalStrut_2 = Box.createHorizontalStrut(15);
        bottombtnpanel.add(horizontalStrut_2);

        btnDeleteUser = new JButton("Delete user");
        btnDeleteUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                try{
                    int row = userTable.getSelectedRow();
                    if (row < 0){
                        JOptionPane.showMessageDialog(UserManageDialog.this, "You must select a user",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Users user = (Users)userTable.getValueAt(row, UserTableModel.OBJECT_COL);

                    userDao.deleteUser(user);

                    refreshUserView();
                    JOptionPane.showMessageDialog(UserManageDialog.this, "User account deleted.",
                            "User", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(UserManageDialog.this, "Error deleting user: "
                            + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }


            }
        });
        bottombtnpanel.add(btnDeleteUser);
    }

    private void alignTable(){
        //Setting renderer for table cells alignment
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );

        //Centering all columns containing String Data
        userTable.setDefaultRenderer(String.class, centerRenderer);
        userTable.setRowHeight(30);

        //Centering particular columns
        userTable.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        userTable.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        userTable.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
        userTable.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );

        //Set column width
        userTable.getColumnModel().getColumn(0).setPreferredWidth(5);
        userTable.getColumnModel().getColumn(3).setPreferredWidth(100);


        // can use this also
        //foodtable.setDefaultRenderer(foodtable.getColumnClass(0), centerRenderer);

    }

    public void setTableModel() throws SQLException {
        List<Users> list = userDao.getAllUser();
        UserTableModel tableModel = new UserTableModel(list);
        userTable.setModel(tableModel);
        alignTable();
    }

    private void refreshUserView(){
        try {
            tableModel = new UserTableModel(userDao.getAllUser());
            userTable.setModel(tableModel);
            alignTable();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error : " + e, "Error refreshing table view",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}

