package com.zs.codeDojo.controllers.course;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

import com.zs.codeDojo.models.DAO.DBModule;
import com.zs.codeDojo.models.DAO.*;
import com.zs.codeDojo.models.course.JsonUtils;


// @WebServlet("/services/course/getCourse")

public class LevelController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        // String url = request.getRequestURI();
        // System.out.println(url);

        String requestedLevel = request.getParameter("level");
        ServletContext con = request.getServletContext();

        // System.out.println(requestedLevel);

        DBModule db = (DBModule)con.getAttribute("db");

        String url = request.getRequestURL().toString();

        User user = (User)request.getSession(false).getAttribute("user");
        int currentLevel = db.getCurrentLevel(user);

        if (requestedLevel == null || Integer.parseInt(requestedLevel)>currentLevel) {
            response.sendRedirect(url+"?level="+currentLevel);
            return;
        }

        CourseQuestion question = db.getCourseQuestion(requestedLevel);

        String questionJson = JsonUtils.toJson(question);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(questionJson);
    }
    
}
