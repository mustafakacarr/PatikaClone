package com.clonepatika.Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {
    Connection connect = null;
    Statement statement = null;

    public Connection connect() {
        try {
            connect = DriverManager.getConnection(Config.DB_URL, Config.DB_USERNAME, Config.DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connect;
    }

    public static Connection getConnect() {
        DBConnector db = new DBConnector();
        return db.connect();

    }
}
