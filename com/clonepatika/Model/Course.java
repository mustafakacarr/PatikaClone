package com.clonepatika.Model;

import com.clonepatika.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Course {
    private int id;
    private String name;
    private String lang;
    private int userId;
    private int patikaId;
    private Patika patika;
    private User educator;

    public Course(int id, String name, String lang, int userId, int patikaId) throws SQLException {
        this.id = id;
        this.name = name;
        this.lang = lang;
        this.userId = userId;
        this.patikaId = patikaId;
        this.patika = Patika.getFetch(patikaId);
        this.educator = User.getFetch(userId);
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

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPatikaId() {
        return patikaId;
    }

    public void setPatikaId(int patikaId) {
        this.patikaId = patikaId;
    }

    public Patika getPatika() {
        return patika;
    }

    public void setPatika(Patika patika) {
        this.patika = patika;
    }

    public User getEducator() {
        return educator;
    }

    public void setEducator(User educator) {
        this.educator = educator;
    }

    public static ArrayList<Course> showCourseList() throws SQLException {
        Statement st = DBConnector.getConnect().createStatement();
        Course course = null;
        ArrayList list = new ArrayList();
        ResultSet rs = st.executeQuery("Select * from course");
        while (rs.next()) {
            course = new Course(rs.getInt("id"), rs.getString("name"), rs.getString("lang"), rs.getInt("user_id"), rs.getInt("patika_id"));
            list.add(course);
        }
        return list;
    }

    public static boolean addCourse(Course course) throws SQLException {
        PreparedStatement pr = DBConnector.getConnect().prepareStatement("INSERT INTO course set name=?,lang=?,user_id=?,patika_id=?");
        pr.setString(1, course.getName());
        pr.setString(2, course.getLang());
        pr.setInt(3, course.getUserId());
        pr.setInt(4, course.getPatikaId());

        if (pr.executeUpdate() == 1) return true;
        else return false;
    }

    public static boolean deleteCourse(Course course) throws SQLException {
        PreparedStatement pr = DBConnector.getConnect().prepareStatement("Delete from course where id=?");
        pr.setInt(1, course.getId());
        if (pr.executeUpdate() == 1) return true;
        else return false;
    }

    public static ArrayList getFetch(int patikaId) throws SQLException {
        PreparedStatement pr = DBConnector.getConnect().prepareStatement("Select * from course where patika_id=?");
        pr.setInt(1, patikaId);
        ArrayList list;
        if (pr.executeUpdate() == 1) {
            list = (ArrayList) pr.executeQuery("Select * from course where patika_id=?");
        } else {
            list = null;
        }
        return list;
    }
}
