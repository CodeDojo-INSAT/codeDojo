package com.zs.codeDojo.controllers.admin;

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


public class FetchQD extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int level = Integer.parseInt(request.getParameter("level"));
        ServletContext context = getServletContext();
        response.setContentType("application/json");

        Question question = null;
        DBModule dbModule = (DBModule) context.getAttribute("db");

        question = dbModule.readQuestionFromDatabase(level);

        JsonResponse jsonResponse = null;
        if (question == null) {
            jsonResponse = new JsonResponse(false, "Can't fetch questions", null);
        }
        else {
            JSONObject json = new JSONObject();
            json.put("description", question.getTitle());
            json.put("code", question.getDescription());

            jsonResponse = new JsonResponse(true, "successfully Fetched", json);
        }

        response.getWriter().print(jsonResponse);
    }
}
