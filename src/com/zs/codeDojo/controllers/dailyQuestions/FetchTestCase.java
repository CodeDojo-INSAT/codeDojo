package com.zs.codeDojo.controllers.dailyQuestions;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.zs.codeDojo.models.DAO.DBModule;
import com.zs.codeDojo.models.DAO.JsonResponse;
import com.zs.codeDojo.models.DAO.TestCases;

public class FetchTestCase extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.valueOf(request.getParameter("question_id"));
        response.setContentType("application/json");

        ServletContext context = getServletContext();
        DBModule dbModule = (DBModule) context.getAttribute("db");

        TestCases testCases = dbModule.fetchTestCase(id);
        
        JSONObject json = new JSONObject();
        JsonResponse jsonResponse = null;
        if (testCases != null) {
            String input[] = testCases.getInputs();
            String output[] = testCases.getOutputs();
            String ids[] = testCases.getIds();

            json.put("input", input);
            json.put("output", output);
            json.put("id", ids);
            jsonResponse = new JsonResponse(true, "successfully fetched", json);
        }
        else {
            jsonResponse = new JsonResponse(false, "can't fetch testcases", null);
        }

        response.getWriter().print(jsonResponse);
    }
}
