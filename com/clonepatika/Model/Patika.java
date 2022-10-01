package com.clonepatika.Model;

import com.clonepatika.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Patika {
    private int id;
    private String name;

    public Patika(int id, String name) {
        this.id = id;
        this.name = name;
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


    public static ArrayList<Patika> showPatikaList() throws SQLException {
        Statement st = DBConnector.getConnect().createStatement();
        ResultSet rs = st.executeQuery("Select * from patika");
        Object patikaObject;
        ArrayList patikaList=new ArrayList();
        while (rs.next()){
            patikaObject =new Patika(rs.getInt("id"),rs.getString("name"));
            patikaList.add(patikaObject);
        }
        return patikaList;
    }
}
