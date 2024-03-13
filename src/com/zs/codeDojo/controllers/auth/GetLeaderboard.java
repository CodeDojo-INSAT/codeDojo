package com.zs.codeDojo.controllers.auth;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.zs.codeDojo.models.DAO.DBModule;

@WebServlet("/services/user/getLeaderboard")
public class GetLeaderboard extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = req.getServletContext();
        DBModule dbModule = (DBModule) servletContext.getAttribute("db");

        JSONArray jsonArr = dbModule.getLeaderBoadNinjas();
        resp.getWriter().write(jsonArr.toString());
    }
}
