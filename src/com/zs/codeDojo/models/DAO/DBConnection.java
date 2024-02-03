package com.zs.codeDojo.models.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
// import java.sql.SQLException;

public class DBConnection {
    private String username = "arjun";
    private String password = "password@1";
    private String host = "localhost";
    private int port = 3306;
    private String dbName = "servlet";
    private String url;

    public DBConnection() {
        url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
    }

    public DBConnection(String username, String password, String host, int port, String dbName) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        this.dbName = dbName;
        url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
    }


    public Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
