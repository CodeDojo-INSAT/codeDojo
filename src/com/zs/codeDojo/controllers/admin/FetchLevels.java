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


public class FetchLevels extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int levels = -1;
        ServletContext context = getServletContext();
        response.setContentType("application/json");

        DBModule dbModule = (DBModule) context.getAttribute("db");

        levels = dbModule.fetchLevels();

        JsonResponse jsonResponse = null;
        if (levels > 1) {
            JSONObject json = new JSONObject();
            json.put("levels", levels);

            jsonResponse = new JsonResponse(true, "Successfully levlels fetched", json);
        }
        else {
            jsonResponse = new JsonResponse(false, "Can't fetch levels", null);
        }

        response.getWriter().print(jsonResponse);
    }
}
