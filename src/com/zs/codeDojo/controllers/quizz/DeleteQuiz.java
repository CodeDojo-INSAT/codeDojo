package com.zs.codeDojo.controllers.quizz;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
// import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zs.codeDojo.models.DAO.DBModule;

import java.io.IOException;
import java.io.PrintWriter;
// import java.sql.SQLException;


// @WebServlet("/services/quiz/deleteQuiz.dojo")

public class DeleteQuiz extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String quizID = req.getParameter("quizID");
        PrintWriter pw = res.getWriter();
        ServletContext context = getServletContext();
        DBModule conn = (DBModule)context.getAttribute("db");
        Status result = conn.deleteQuiz(quizID);
        pw.println(result.toJSON());
        
    }
}
