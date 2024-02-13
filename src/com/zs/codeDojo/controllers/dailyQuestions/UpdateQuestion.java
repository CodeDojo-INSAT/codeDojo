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

public class UpdateQuestion extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        response.setContentType("application/json");

        JSONObject json = processRequest(request);
        int id = json.getInt("question_id");
        String title = json.getString("title");
        String question = json.getString("question");

        DBModule dbModule = (DBModule) context.getAttribute("db");

        json.clear();
        JsonResponse jsonResponse = null;
        if (dbModule.updateQuestion(id, title, question)) {
            jsonResponse = new JsonResponse(true, "successfully question updated", null);
        }
        else {
            jsonResponse = new JsonResponse(false, "updating question fails", null);
        }

        response.getWriter().print(jsonResponse);
    }

    private JSONObject processRequest(HttpServletRequest req) throws IOException {
        Scanner sc = new Scanner(req.getInputStream());

        String content = "";
        while (sc.hasNextLine()) {
            content += sc.nextLine();
        }
        sc.close();

        return new JSONObject(content);
    }
}
