package com.zs.codeDojo.models.dailyQuestions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.zs.codeDojo.models.DAO.DBConnection;

public class Config implements ServletContextListener {
    private final String DELETE_PROCEDURE = "DROP PROCEDURE IF EXISTS publish_question;";
    private final String DELETE_EVENT = "DROP EVENT IF EXISTS schedule_publish_event;";
    private final String procedureScript = "/opt/apache/webapps/dailyQuestions/Java/procedure.sql";
    private final String eventScript = "/opt/apache/webapps/dailyQuestions/Java/event.sql";
    private final String logPath = "error.log";
    private String script;
    private OutputStream originalErr = null;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        originalErr = System.err;
        ServletContext context = sce.getServletContext();
        
        Connection conn = new DBConnection().getConnection();
        context.setAttribute("conn", conn);

        try {
            System.setErr(new PrintStream(new FileOutputStream(logPath)));

            deleteOldEventsAndProcedures(conn);
            createProcedure(conn);
            createEvent(conn);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
    
    public void restoreErr() {
        System.setErr(new PrintStream(originalErr));
    }

    public void createProcedure(Connection conn) {
        try (Statement statement = conn.createStatement()) {
            script = "";
            readScript(procedureScript);

            statement.execute(script);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createEvent(Connection conn) {
        try (Statement statement = conn.createStatement()) {
            script = "";
            readScript(eventScript);

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
        try (Statement statement = conn.createStatement()){
            if (statement.executeUpdate(DELETE_PROCEDURE) != 0)  {
                if (statement.executeUpdate(DELETE_EVENT) != 0) {
                    return true;
                }
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
