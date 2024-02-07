package com.zs.codeDojo.controllers.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zs.codeDojo.models.DAO.DBModule;


public class FetchLevels extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int levels = -1;
        ServletContext context = getServletContext();
        response.setContentType("application/json");

        try {
            DBModule dbModule = (DBModule) context.getAttribute("db");

            levels = dbModule.fetchLevels();
        }
        catch (Exception sqlEx) {
            sqlEx.printStackTrace();
        }

        PrintWriter writer = response.getWriter();
        writer.println("{'level':" + levels + "}");

        writer.close();
    }
}
