package com.zs.codeDojo.controllers.main;

// import java.io.File;
// import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import com.zs.codeDojo.models.DAO.DBModule;
import com.zs.codeDojo.models.DAO.JsonResponse;
import com.zs.codeDojo.models.DAO.Question;


public class GetQuest extends HttpServlet {
    // private String path;
    // private String[] valueStrings;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Question question = processRequest(request);

        setResponseHeader(response);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();

        JSONObject jsonObject = new JSONObject();
        JsonResponse jsonResponse = null;

        if (question.getTitle() != null && question.getDescription() != null) {
            jsonObject.put("questionDescription", question.getDescription());
            jsonObject.put("questionCode", question.getTitle());

            jsonResponse = new JsonResponse(true, "successfully fetched question", jsonObject);            
        } else {
            jsonResponse = new JsonResponse(false, "can't fetch question", null);
        }

        writer.print(jsonResponse);
    }

    private void setResponseHeader(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers",
                "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, level");
    }

    private Question processRequest(HttpServletRequest request) {
        int level = Integer.valueOf(request.getParameter("level"));

        DBModule dbModule = (DBModule) request.getServletContext().getAttribute("db");

        return dbModule.readContentFromDatabase(level);
    }
}
