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

public class GetUserStreak extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();

        DBModule dbModule = new DBModule((Connection) context.getAttribute("conn"));

        int streak = dbModule.getStreakForUser(1);

        JSONObject json = new JSONObject();
        json.put("streak", streak);

        response.getWriter().write(json.toString());
        
    }
}
