package org.troy.model;

import org.troy.database.entity.Users;

import java.util.List;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class UserTableModel extends AbstractTableModel {

    public static final int OBJECT_COL = -1;
    private static final int USER_ID = 0;
    private static final int FIRST_NAME = 1;
    private static final int LAST_NAME = 2;
    private static final int USERNAME = 3;
    private static final int PASSWORD = 4;
    private static final int PERMISSION = 5;

    private String columnNames[] = {"ID", "First Name", "Last Name", "Username", "Password", "Permission"};

    private List<Users> usersList;

    public UserTableModel(List<Users> list){
        usersList = list;
    }

    public int getRowCount(){
        return usersList.size();
    }

    public int getColumnCount(){
        return columnNames.length;
    }

    public String getColumnName(int col){
        return columnNames[col];
    }

    public Object getValueAt(int row, int col){
        Users temp = usersList.get(row);
        switch(col){
            case USER_ID:
                return temp.getId();
            case FIRST_NAME:
                return temp.getFirstName();
            case LAST_NAME:
                return temp.getLastName();
            case USERNAME:
                return temp.getUsername();
            case PASSWORD:
                return temp.getPassword();
            case PERMISSION:
                return temp.getPermission();
            case OBJECT_COL:
                return temp;
            default:
                return temp.getId();
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Class getColumnClass(int col){
        return getValueAt(0, col).getClass();
    }
}

