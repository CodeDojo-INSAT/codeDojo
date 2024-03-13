package com.zs.codeDojo.controllers.main;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.zs.codeDojo.models.DAO.DBModule;
import com.zs.codeDojo.models.DAO.User;

@WebServlet("/services/course/getSubmission")
public class GetUserSubmission extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        ServletContext con = request.getServletContext();
        DBModule db = (DBModule)con.getAttribute("db");
        
        User usr = (User)request.getSession(false).getAttribute("user");
        // String use = usr.getUsername();
        String code = db.getSubmission(usr,Integer.valueOf(request.getParameter("level")));
        JSONObject json = new JSONObject();
        if (code!=null) {
            
            json.put("code",code);
        }
        else{
            json.put("error","No submission found");
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
    }
}
