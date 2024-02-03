package com.zs.codeDojo.controllers.dailyQuestions;

import java.io.IOException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class CheckAnswer extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = processRequest(request);
        
        // String userJavaCode = json.getString("javaCode");
        json.clear();

        String result = "[1,0,1,1,1,0]";
        json.put("result", "[1,1,1,1,0]");
        
        if (!result.contains("0")) {
            // increaseDailyStreak();
        }

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
