package com.zs.codeDojo.controllers.dailyQuestions;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zs.codeDojo.models.DAO.DBModule;
import com.zs.codeDojo.models.DAO.JsonResponse;

public class DeleteQuestion extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        response.setContentType("application/json");
        
        int questionId = Integer.valueOf(request.getParameter("id"));

        DBModule dbModule = (DBModule) context.getAttribute("db");
        
        boolean message = dbModule.deleteQuestion(questionId);
        
        JsonResponse jsonResponse = null;
        if (message) {
            jsonResponse = new JsonResponse(false, "can't delete question", null);
        }
        else {
            jsonResponse = new JsonResponse(true, "deleted successfully", null);
        }
        
        response.getWriter().print(jsonResponse);
    }
}
