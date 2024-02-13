package com.zs.codeDojo.controllers.quizz;

import org.json.JSONArray;
// import org.json.JSONObject;

import com.zs.codeDojo.models.DAO.DBModule;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


// @WebServlet("/services/quiz/getQuizzes.dojo")
public class GetQuizzes extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ServletContext context = getServletContext();
        res.setContentType("application/json");

        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET");
        res.setHeader("Access-Control-Allow-Headers", "Content-Type");
        try {
            PrintWriter pw = res.getWriter();
            // DBConnector conn = new DBConnector("mark.main", "plsletmein123#");
            DBModule conn = (DBModule)context.getAttribute("db");
            JSONArray quizzes = conn.getQuizzes();

            pw.println(quizzes.toString());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
