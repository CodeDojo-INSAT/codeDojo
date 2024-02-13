package com.zs.codeDojo.controllers.dailyQuestions;

import java.io.IOException;
import java.util.Scanner;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zs.codeDojo.models.DAO.DBModule;
import com.zs.codeDojo.models.DAO.JsonResponse;

public class UpdateTestcase extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        response.setContentType("application/json");

        JSONObject json = processRequest(request);

        JSONArray tcIds = json.getJSONArray("tcIds");
        JSONArray inputs = json.getJSONArray("inputs");
        JSONArray outputs = json.getJSONArray("outputs");

        DBModule dbModule = (DBModule) context.getAttribute("db");
        boolean status = true;
        for (int i=0; i<tcIds.length(); i++) {
            if (!dbModule.updateTestcases(tcIds.getInt(i), inputs.getString(i), outputs.getString(i), i==tcIds.length()-1)) {
                status = false;
                break;
            }
        }

        json.clear();
        
        JsonResponse jsonResponse = null;
        if (status) {
            jsonResponse = new JsonResponse(true, "testcases updated", null);
        }
        else {
            jsonResponse = new JsonResponse(false, "can't update testcases", null);
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
