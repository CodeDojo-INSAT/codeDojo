package com.zs.codeDojo.controllers.admin;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.zs.codeDojo.models.DAO.DBModule;
import com.zs.codeDojo.models.DAO.JsonResponse;

// @WebServlet("/fetchCheckers")
public class FetchCheckers extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ServletContext context = getServletContext();
        res.setContentType("application/json");

        DBModule dbModule = (DBModule) context.getAttribute("db");

        String[] checkersName = dbModule.fetchCheckers();
        
        JsonResponse jsonResponse = null;
        if (checkersName != null) {
            JSONObject json = new JSONObject();
            json.put("checkers", checkersName);

            jsonResponse = new JsonResponse(true, "Checkers fetched", json);
        }
        else {
            jsonResponse = new JsonResponse(false, "can't fetch checker", null);
        }

        res.getWriter().print(jsonResponse);
    }
}
