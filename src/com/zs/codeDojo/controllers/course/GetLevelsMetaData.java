package com.zs.codeDojo.controllers.course;

import com.zs.codeDojo.models.DAO.DBModule;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
// import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// @WebServlet("/services/course/getCourseData")

public class GetLevelsMetaData extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                
        ServletContext con = request.getServletContext();
        DBModule db = (DBModule)con.getAttribute("db");

        String CourseMetaData = db.getCourseMetaData().toString();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(CourseMetaData);

    }

    
}