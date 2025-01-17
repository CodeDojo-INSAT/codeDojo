package com.zs.codeDojo.controllers.dailyQuestions;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.zs.codeDojo.models.DAO.DBModule;
import com.zs.codeDojo.models.DAO.JsonResponse;
import com.zs.codeDojo.models.DAO.User;

public class GetUserStreak extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        response.setContentType("application/json");

        DBModule dbModule = (DBModule) context.getAttribute("db");

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        int streak = dbModule.getStreakForUser(user.getUsername());

        JsonResponse jsonResponse = null;
        if (streak >= 0) {
            JSONObject json = new JSONObject();
            json.put("streak", streak);

            jsonResponse = new JsonResponse(true, "User streak fetched", json);
        }
        else {
            jsonResponse = new JsonResponse(false, "can't fetch user streak", null);
        }
        
        response.getWriter().print(jsonResponse);
    }
}
