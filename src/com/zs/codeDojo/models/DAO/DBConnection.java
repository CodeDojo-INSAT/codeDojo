package com.zs.codeDojo.models.DAO;

import java.sql.Connection;
import java.sql.DriverManager;

import com.zs.codeDojo.properties.Properties;

public class DBConnection {
    private Connection conn = null;

    public Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://" + Properties.db_host + ":" + Properties.db_port + "/" + Properties.db_name, Properties.db_username, Properties.db_password);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
}
