package com.zs.codeDojo.controllers.quizz;

import org.json.JSONObject;

import com.zs.codeDojo.models.DAO.DBModule;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
// import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
// import java.sql.SQLException;


// @WebServlet("/services/quiz/checkAnswer.dojo")
public class CheckAnswer extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String quizID = req.getParameter("quizID");
        String questionID = req.getParameter("questionID");
        String answer = req.getParameter("answer");

        System.out.println(" " + quizID + " " + questionID + " " + answer);
        JSONObject response = new JSONObject();
        PrintWriter pw = res.getWriter();
        ServletContext context = getServletContext();
        DBModule conn = (DBModule)context.getAttribute("db");
        response.put("isAnswer", conn.isCorrectAnswer(quizID, questionID, answer));

        pw.println(response);

    }
}
