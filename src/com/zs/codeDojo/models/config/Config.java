package com.zs.codeDojo.models.config;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.zs.codeDojo.models.DAO.DBConnection;
import com.zs.codeDojo.models.DAO.DBModule;
import com.zs.codeDojo.models.DAO.IOStreams;
import com.zs.codeDojo.properties.Properties;
import com.zs.codeDojo.properties.SQLQueries;

public class Config implements ServletContextListener {
    private String script;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        IOStreams streams = new IOStreams(System.in, System.out, System.err);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        streams.setCustomOut(out);
        System.setOut(new PrintStream(out));

        Connection conn = new DBConnection().getConnection();

        context.setAttribute("db", new DBModule(conn));
        context.setAttribute("streams", streams);

        try {
            System.setErr(new PrintStream(new FileOutputStream(Properties.logPath, true))); 

            deleteOldEventsAndProcedures(conn);
            createProcedure(conn);
            createEvent(conn);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}

    public void createProcedure(Connection conn) {
        try (Statement statement = conn.createStatement()) {
            script = "";
            readScript(Properties.procedureScript);

            statement.execute(script);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createEvent(Connection conn) {
        try (Statement statement = conn.createStatement()) {
            script = "";
            readScript(Properties.eventScript);

            statement.execute(script);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void readScript(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));

        String line;
        while ((line = reader.readLine()) != null) {
            script += line + "\n";
        }

        reader.close();
    }

    private boolean deleteOldEventsAndProcedures(Connection conn) {
        boolean status = false;
        try (PreparedStatement statement = conn.prepareStatement(SQLQueries.DELETE_PROCEDURE)){
            conn.setAutoCommit(false);
            if (statement.execute())  {
                try (PreparedStatement statement1 = conn.prepareStatement(SQLQueries.DELETE_EVENT)) {
                    if (statement.execute()) {
                        status = true;
                    }
                } 
            }
            conn.commit();
        }
        catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
        }
        return status;
    }
}
