package com.zs.codeDojo.models.dailyQuestions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ScheduleQuestion {
    private final Connection conn;
    private String script;
    private final String scriptPath = "/opt/apache/webapps/dailyQuestions/Java/scheduleQuestion.sql";
    private final String GET_EVENT_ID = "SELECT count(event_name) FROM information_schema.events";


    public ScheduleQuestion(Connection conn) {
        this.conn = conn;
    }

    public void schedule() {
        try (Statement statement = conn.createStatement()){
            readScript();

            script.replace("#eventId#", String.valueOf(getEventId()));

            statement.execute(script);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readScript() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(scriptPath));

        String line;
        while ((line = reader.readLine()) != null) {
            script += line + "\n";
        }

        reader.close();
    }

    private int getEventId() {
        int id = 0;
        try (PreparedStatement statement = conn.prepareStatement(GET_EVENT_ID)) {
            if (statement.execute()) {
                ResultSet rs = statement.getResultSet();

                while (rs.next()) {
                    id = rs.getInt(1);
                }
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return id + 1;
    }
}
