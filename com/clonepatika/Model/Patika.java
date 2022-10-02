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
    public static boolean addPatika(Patika patika) throws SQLException{
        PreparedStatement pr=DBConnector.getConnect().prepareStatement("INSERT INTO patika set name=?");
        pr.setString(1,patika.getName());
        if (pr.executeUpdate()==1) return true; else return false;
    }
    public static boolean updatePatika(Patika patika) throws SQLException{
        PreparedStatement pr=DBConnector.getConnect().prepareStatement("Update patika set name=? where id=?");
        pr.setString(1,patika.getName());
        pr.setInt(2,patika.getId());
        if (pr.executeUpdate()==1) {
            return true;
        }  else return false;

    }
    public static Patika getFetch(int id) throws SQLException{
        PreparedStatement pr=DBConnector.getConnect().prepareStatement("Select * from patika where id=?");
        pr.setInt(1,id);
        ResultSet result=pr.executeQuery();
        Patika patika=null;
        while (result.next()){
            patika=new Patika(result.getInt(1),result.getString(2));
        }
        return patika;
    }
    public static boolean deletePatika(int id) throws SQLException{
        PreparedStatement pr=DBConnector.getConnect().prepareStatement("Delete from patika where id=?");
        pr.setInt(1,id);
        if(pr.executeUpdate()==1) {

            return true;
        } else{
            return false;
        }
    }
}
