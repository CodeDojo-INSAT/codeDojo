package com.zs.codeDojo.controllers.admin;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zs.codeDojo.models.DAO.DBModule;
import com.zs.codeDojo.models.DAO.JsonResponse;


public class UploadQD extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        JSONObject jsonObject = new JSONObject(processRequest(request));
        ServletContext context = getServletContext();

        String question = jsonObject.getString("questionCode");
        String desc = jsonObject.getString("description");
        JSONArray checkers = jsonObject.getJSONArray("checkers");
        int[] checkerIds = convertJsonToIntArray(checkers);

        DBModule dbModule = (DBModule) context.getAttribute("db");

        JsonResponse jsonResponse = null;
        if (!dbModule.uploadLevel(desc, question, checkerIds)) {
            jsonResponse = new JsonResponse(false, "upload Question fails", null);
        }
        else {
            jsonResponse = new JsonResponse(true, "successfully uploaded", null);
        }

        response.getWriter().print(jsonResponse);
    }

    private int[] convertJsonToIntArray(JSONArray source) {
        int[] arr = new int[source.length()];
        for (int i=0; i<source.length(); i++) {
            arr[i] = source.getInt(i);
        }
        return arr;
    }

    private String processRequest(HttpServletRequest request) throws IOException {
        String data = "";
        BufferedReader reader = request.getReader();

        String line;
        while ((line = reader.readLine()) != null) {
            data += line+"\n";
        }
        return data;
    } 
}
