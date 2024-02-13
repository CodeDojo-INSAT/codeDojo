package com.zs.codeDojo.controllers.dailyQuestions;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zs.codeDojo.models.DAO.DBModule;
import com.zs.codeDojo.models.DAO.JsonResponse;

public class FetchAllTitles extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        response.setContentType("application/json");

        DBModule dbModule = (DBModule) context.getAttribute("db");
        
        String[] title = dbModule.fetchAllTitles();

        JsonResponse jsonResponse = null;
        if (title.length < 1) {
            jsonResponse = new JsonResponse(false, "can't fetch titles", null);
        }
        else {
            jsonResponse = new JsonResponse(true, "Titles fetched successfully", title);
        }

        response.getWriter().print(jsonResponse);
    } 
}
