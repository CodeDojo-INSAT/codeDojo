package com.zs.codeDojo.controllers.dailyQuestions;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.zs.codeDojo.models.DAO.DBModule;
import com.zs.codeDojo.models.dailyQuestions.Question;

public class GetQuestion extends HttpServlet { 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        Connection conn = (Connection) context.getAttribute("conn");

        JSONObject json = new JSONObject();
        DBModule dbModule = new DBModule(conn);

        if (dbModule.fetchTodayQuestion()) {
            Question todayQuestion = dbModule.getTodayQuestion();

            json.put("title", todayQuestion.getTitle());
            json.put("description", todayQuestion.getDescription());
        }
        else {
            if (dbModule.hasPublishingTime()) {
                json.put("publishing_time", dbModule.getPublishingTime());
            }
            else {
                json.put("error", "today question is not prepared yet.");
            }
        }
        
        response.getWriter().write(json.toString());
    }
}
