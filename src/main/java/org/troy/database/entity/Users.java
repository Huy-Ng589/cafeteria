package org.troy.database.entity;

public class Users {

    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String permission;

    public Users(String fname, String lname, String uname, String pwd, String perm){
        firstName = fname;
        lastName = lname;
        username = uname;
        password = pwd;
        permission = perm;
    }

    public Users(int id, String fname, String lname, String uname, String pwd, String perm){
        this(fname, lname, uname, pwd, perm);
        this.id = id;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getLastName(){
        return lastName;
    }
    public void setLastName(String name){
        lastName = name;
    }

    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String name){
        firstName = name;
    }

    public String getUsername(){
        return username;
    }
    public void setUsername(String mail){
        username = mail;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String pwd){
        password = pwd;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String toString(){
        return String.format("User [Id = %s, First name = %s, Last name = %s, Username = %s, Permission = %s]",
                id, firstName, lastName, username, permission);
    }
}
