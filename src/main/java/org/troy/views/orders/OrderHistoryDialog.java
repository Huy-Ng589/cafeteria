package org.troy.views.orders;

import org.troy.controller.BillingApp;
import org.troy.database.daoimpl.OrderDaoImpl;
import org.troy.database.entity.Order;
import org.troy.database.entity.Users;
import org.troy.model.OrderHistoryTableModel;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableCellRenderer;

import javax.swing.JLabel;
import java.awt.Font;
import java.sql.SQLException;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class OrderHistoryDialog extends JDialog {
    private BillingApp frame;

    private OrderDaoImpl orderDAO;
    private JTable orderHistoryTable;
    private Users users;

    private JPanel buttonPanel;

    public OrderHistoryDialog(BillingApp frame, OrderDaoImpl orderDAO, Users users){
        this();
        this.frame = frame;
        this.orderDAO = orderDAO;
        this.users = users;
    }

    public OrderHistoryDialog() {
        setTitle("HuyNQ Cafeteria");
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());

        JLabel lblPastOrderDetails = new JLabel("Past Order Details");
        lblPastOrderDetails.setHorizontalAlignment(SwingConstants.CENTER);
        lblPastOrderDetails.setFont(new Font("Sylfaen", Font.BOLD, 18));
        getContentPane().add(lblPastOrderDetails, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane();
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        orderHistoryTable = new JTable();
        scrollPane.setViewportView(orderHistoryTable);

        buttonPanel = new JPanel();
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
                frame.setVisible(true);

            }
        });
        buttonPanel.add(btnBack);
    }

    public void setTableModel() throws SQLException{
        List<Order> list = orderDAO.getOrderHistory(users);
        OrderHistoryTableModel tableModel = new OrderHistoryTableModel(list);
        orderHistoryTable.setModel(tableModel);
        alignTable();
    }

    private void alignTable(){
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for(int i=0; i< orderHistoryTable.getColumnCount(); i++){
            orderHistoryTable.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
        }
    }

}

