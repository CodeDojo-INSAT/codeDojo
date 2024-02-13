package com.zs.codeDojo.controllers.admin;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.zs.codeDojo.models.DAO.DBModule;
import com.zs.codeDojo.models.DAO.JsonResponse;


public class UpdateQD extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String content = processRequest(request);
        ServletContext context = getServletContext();

        JSONObject jsonObject = new JSONObject(content);

        String desc = jsonObject.getString("description");
        String quest = jsonObject.getString("questionCode");
        int level = Integer.parseInt(jsonObject.getString("level"));

        DBModule dbModule = (DBModule) context.getAttribute("db");
        JsonResponse jsonResponse = null;
        
        if (!dbModule.updateLevel(desc, quest, level)) {
            jsonResponse = new JsonResponse(false, "Update question failed", null);
        }
        else {
            jsonResponse = new JsonResponse(true, "successfully updated", null);
        }
        
        response.getWriter().print(jsonResponse);
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
