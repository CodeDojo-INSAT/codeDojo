package com.zs.codeDojo.controllers.admin;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.zs.codeDojo.models.DAO.DBModule;

@WebServlet("/fetchCheckers")
public class FetchCheckers extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ServletContext context = getServletContext();

        DBModule dbModule = null;
        try {
            dbModule = (DBModule) context.getAttribute("db");

            String checkersName = dbModule.fetchCheckers();
            if (checkersName != null) {
                writeAsJson(checkersName, res);
            }
            else {
                writeAsJson("null", res);
            }
        }
        catch (Exception sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    private void writeAsJson(String data, HttpServletResponse res) throws IOException {
        JSONObject json = new JSONObject();

        json.put("checkers", data);
        res.getWriter().println(json.toString());
    }
}
