package com.zs.codeDojo.controllers.dailyQuestions;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.zs.codeDojo.models.DAO.DBModule;
import com.zs.codeDojo.models.DAO.JsonResponse;
import com.zs.codeDojo.models.DAO.Question;

public class GetQuestion extends HttpServlet { 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        response.setContentType("application/json");

        JSONObject json = new JSONObject();
        DBModule dbModule = (DBModule) context.getAttribute("db");

        JsonResponse jsonResponse = null;
        if (dbModule.fetchTodayQuestion()) {
            Question todayQuestion = dbModule.getTodayQuestion();

            json.put("title", todayQuestion.getTitle());
            json.put("description", todayQuestion.getDescription());

            jsonResponse = new JsonResponse(true, "successfully fetched today question", json);
        }
        else {
            if (dbModule.hasPublishingTime()) {
                json.put("publishing_time", dbModule.getPublishingTime());
                jsonResponse = new JsonResponse(false, "question is not publish yet", json);
            }
            else {
                jsonResponse = new JsonResponse(false, "today have no question", null);
            }
        }
        
        response.getWriter().print(jsonResponse);
    }
}
