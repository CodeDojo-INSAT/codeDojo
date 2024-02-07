package com.zs.codeDojo.controllers.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.zs.codeDojo.models.DAO.DBModule;
import com.zs.codeDojo.models.DAO.Question;


public class FetchQD extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int level = Integer.parseInt(request.getParameter("level"));
        ServletContext context = getServletContext();
        response.setContentType("application/json");

        Question question = null;
        DBModule dbModule;

        try {
            dbModule = (DBModule) context.getAttribute("db");

            question = dbModule.readQuestionFromDatabase(level);

            if (question == null) {
                return;
            }
        }
        catch (Exception sqlEx) {
            sqlEx.printStackTrace();
            return;
        }

        response.setContentType("application/json");
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("description", question.getTitle());
        jsonObj.put("questionCode", question.getDescription());

        PrintWriter writer = response.getWriter();
        
        writer.println(jsonObj.toString());

        writer.close();
    }
}
