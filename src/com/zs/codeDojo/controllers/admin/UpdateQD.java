package com.zs.codeDojo.controllers.admin;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.zs.codeDojo.models.DAO.DBModule;


public class UpdateQD extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String content = processRequest(request);
        ServletContext context = getServletContext();

        JSONObject jsonObject = new JSONObject(content);

        String desc = jsonObject.getString("description");
        String quest = jsonObject.getString("questionCode");
        int level = Integer.parseInt(jsonObject.getString("level"));

        DBModule dbModule = null;
        jsonObject.clear();
        try {
            dbModule = (DBModule) context.getAttribute("db");

            if (!dbModule.updateLevel(desc, quest, level)) {
                jsonObject.put("status", false);
            }
        }
        catch (Exception sqlEx) {
            sqlEx.printStackTrace();
        }

        response.setContentType("application/json");
        jsonObject.put("status", true);
        
        response.getWriter().write(jsonObject.toString());
    }

    public String processRequest(HttpServletRequest request) throws IOException {
        String content = "";
        BufferedReader reader = request.getReader();

        String line;
        while((line = reader.readLine()) != null) {
            content += line;
        }
        return content;
    }
}
