package com.zs.codeDojo.controllers.dailyQuestions;

import java.io.IOException;
import java.util.Scanner;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.zs.codeDojo.models.DAO.DBModule;
import com.zs.codeDojo.models.DAO.JsonResponse;

public class PostQuestion extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = processRequest(request);
        response.setContentType("application/json");

        ServletContext context = getServletContext();
        
        DBModule dbModule = (DBModule) context.getAttribute("db");
        
        boolean status = dbModule.addQuestion(json, json.has("testCases"));

        json.clear();
        JsonResponse jsonResponse = null;
        
        if (!status) {
            json.put("error", dbModule.getError());
            jsonResponse = new JsonResponse(false, "can't add question", json);
        }
        else {
            jsonResponse = new JsonResponse(true, "successfully added", null);
        }
        
        response.getWriter().print(jsonResponse);
    }

    private JSONObject processRequest(HttpServletRequest req) throws IOException {
        Scanner sc = new Scanner(req.getInputStream());

        String content = "";
        while (sc.hasNextLine()) {
            content += sc.nextLine() + "\n";
        }
        sc.close();

        return new JSONObject(content);
    }
}