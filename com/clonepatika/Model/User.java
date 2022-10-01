package com.clonepatika.Model;

import com.clonepatika.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String username;
    private String password;
    private String type;


    public User(int id, String name, String username, String password, String type) {
        this.id = id;
        this.setName(name);
        this.setUsername(username);
        this.setPassword(password);
        this.setType(type);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static ArrayList<User> showUsers() throws SQLException {
        Statement statement = DBConnector.getConnect().createStatement();
        ResultSet rs = statement.executeQuery("Select * from user");
        ArrayList<User> list = new ArrayList<>();
        User userObject;
        while (rs.next()) {
            userObject = new User(rs.getInt("id"), rs.getString("name"), rs.getString("username"), rs.getString("password"), rs.getString("type"));
            list.add(userObject);
        }
        return list;
    }

    public static boolean addUser(User e) throws SQLException {
        if (getFetch(e.getUsername()) != null) {
            return false;
        }

        PreparedStatement prStatement = DBConnector.getConnect().prepareStatement("INSERT INTO user set name=?,username=?,password=?,type=?");
        prStatement.setString(1, e.getName());
        prStatement.setString(2, e.getUsername());
        prStatement.setString(3, e.getPassword());
        prStatement.setString(4, e.getType());
        if (prStatement.executeUpdate() == 1) return true;
        else return false;
    }

    public static User getFetch(String username) throws SQLException {
        User userObject = null;
        PreparedStatement prStatement = DBConnector.getConnect().prepareStatement("Select * from user where username=?");
        prStatement.setString(1, username);
        ResultSet rs = prStatement.executeQuery();
        if (rs.next()) {
            userObject = new User(rs.getInt("id"), rs.getString("name"), rs.getString("username"), rs.getString("password"), rs.getString("type"));
        }
        return userObject;
    }

    public static boolean deleteUser(int id) throws SQLException {
        PreparedStatement pr = DBConnector.getConnect().prepareStatement("Delete from user where id=?");
        pr.setInt(1, id);
        System.out.println(pr);
        System.out.println(pr.execute());
        return pr.executeUpdate() != -1;
    }

    public static boolean updateUser(User user) throws SQLException {
        PreparedStatement pr = DBConnector.getConnect().prepareStatement("Update user set name=?,username=?,password=?,type=? where id=?");
        pr.setString(1, user.getName());
        pr.setString(2, user.getUsername());
        pr.setString(3, user.getPassword());
        pr.setString(4, user.getType());
        pr.setInt(5, user.getId());
        if (pr.executeUpdate() != -1) return true;
        else return false;

    }

    public static ArrayList<User> searchUser(String query) throws SQLException {
        Statement statement = DBConnector.getConnect().createStatement();
        ResultSet rs = statement.executeQuery(query);
        ArrayList<User> list = new ArrayList<>();
        User userObject;
        while (rs.next()) {
            userObject = new User(rs.getInt("id"), rs.getString("name"), rs.getString("username"), rs.getString("password"), rs.getString("type"));
            list.add(userObject);
        }
        return list;
    }

    public static String searchQuery(String name, String username, String type) {
        String query = "SELECT * FROM user where username LIKE '%{{username}}%' AND name LIKE '%{{name}}%' AND type='{{type}}'";
        query = query.replace("{{username}}", username);
        query = query.replace("{{name}}", name);
       query = query.replace("{{type}}", type);

        return query;
    }
}
   //