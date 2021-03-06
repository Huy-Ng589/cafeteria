package org.troy.views.admin;

import org.troy.controller.BillingAdminApp;
import org.troy.database.daoimpl.ItemDaoImpl;
import org.troy.database.entity.Users;
import org.troy.model.FoodTableModel;

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

@SuppressWarnings("serial")
public class FoodMenuAdminDialog extends JDialog {

    private BillingAdminApp frame;

    private ItemDaoImpl foodDAO;
    private Users users;

    private final JPanel contentPanel = new JPanel();
    JScrollPane tabelscrollPane;
    private JTextField textField;

    private JTable foodtable;
    private FoodTableModel tableModel;

    JButton btnBack;
    JButton btnAddNew;


    public FoodMenuAdminDialog(final BillingAdminApp frame, final ItemDaoImpl foodDAO, final Users users) {

        this.frame = frame;
        this.foodDAO = foodDAO;
        this.users = users;

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent we)
            {
                int PromptResult = JOptionPane.showConfirmDialog(null, "Exit application ?",
                        "Confirm exit", JOptionPane.OK_CANCEL_OPTION);
                if(PromptResult == JOptionPane.OK_OPTION) {
                    foodDAO.vacateQuantityColumn();
                    refreshFoodItemView();
                    System.exit(0);
                }
            }
        });

        setTitle("Cafeteria System - Item Menu");
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

        JLabel lblFoodOrderMenu = new JLabel("Drink Order Menu");
        lblFoodOrderMenu.setHorizontalAlignment(SwingConstants.CENTER);
        lblFoodOrderMenu.setFont(new Font("Tahoma", Font.BOLD, 15));
        toplabelpanel.add(lblFoodOrderMenu);

        Component rigidArea = Box.createRigidArea(new Dimension(20, 5));
        toplabelpanel.add(rigidArea);

        JLabel lblPleaseSelectThe = new JLabel("Modify Menu");
        lblPleaseSelectThe.setHorizontalAlignment(SwingConstants.CENTER);
        lblPleaseSelectThe.setFont(new Font("Tahoma", Font.BOLD, 12));
        toplabelpanel.add(lblPleaseSelectThe);

        tabelscrollPane = new JScrollPane();
        centerpanel.add(tabelscrollPane, BorderLayout.CENTER);

        foodtable = new JTable();

        try{
            tableModel = new FoodTableModel(foodDAO.getAllItems());
            foodtable.setModel(tableModel);

            //tabelscrollPane.add(foodtable.getTableHeader(), BorderLayout.NORTH);
            tabelscrollPane.setViewportView(foodtable);
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

        Component horizontalStrut_1 = Box.createHorizontalStrut(320);
        bottombtnpanel.add(horizontalStrut_1);

        btnAddNew = new JButton("Add new drink");
        btnAddNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddNewItemDialog dialog = new AddNewItemDialog(FoodMenuAdminDialog.this, foodDAO);
                dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

                //dissolve current dialog and create new dialog
                //dispose();
                //setVisible(false);    can use this also but dispose() is preferred to release memory
                dialog.setVisible(true);
                refreshFoodItemView();
            }
        });
        bottombtnpanel.add(btnAddNew);
    }

    private void alignTable(){
        //Setting renderer for table cells alignment
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );

        //Centering all columns containing String Data
        foodtable.setDefaultRenderer(String.class, centerRenderer);
        foodtable.setRowHeight(90);

        //Centering particular columns
        foodtable.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        foodtable.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        foodtable.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
        foodtable.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );

        //Set column width
        foodtable.getColumnModel().getColumn(0).setPreferredWidth(5);
        foodtable.getColumnModel().getColumn(3).setPreferredWidth(100);


        // can use this also
        //foodtable.setDefaultRenderer(foodtable.getColumnClass(0), centerRenderer);

    }

    public void refreshFoodItemView(){
        try {
            tableModel = new FoodTableModel(foodDAO.getAllItems());
            foodtable.setModel(tableModel);
            alignTable();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error : " + e, "Error refreshing table view",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}

