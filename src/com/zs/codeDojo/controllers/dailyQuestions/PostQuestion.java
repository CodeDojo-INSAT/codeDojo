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

public class PostQuestion extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = processRequest(request);

        ServletContext context = getServletContext();
        
        DBModule dbModule = (DBModule) context.getAttribute("db");
        
        boolean status = dbModule.addQuestion(json, json.has("testCases"));

        json.clear();
        
        if (!status) {
            System.err.println("err " + status);
            json.put("error", dbModule.getError());
        }
        json.put("result", status);

        response.getWriter().write(json.toString());
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