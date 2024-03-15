package com.zs.codeDojo.controllers.quizz;

// import org.json.JSONArray;
import org.json.JSONObject;

import com.zs.codeDojo.models.DAO.DBModule;
import com.zs.codeDojo.models.DAO.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/services/quiz/getQuizProgress.dojo")
public class GetQuizProgress extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ServletContext context = getServletContext();
        res.setContentType("application/json");
        JSONObject obj = new JSONObject();
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET");
        res.setHeader("Access-Control-Allow-Headers", "Content-Type");
        try {
            PrintWriter pw = res.getWriter();
            // DBConnector conn = new DBConnector("mark.main", "plsletmein123#");
            DBModule conn = (DBModule)context.getAttribute("db");
            User user = (User) req.getSession().getAttribute("user");
            int quizProgress = conn.getQuizProgress(user);

            int quizzes = conn.getQuizzes(user).length();
            obj.put("quizCount", quizzes);
            obj.put("completedCount", quizProgress);

            pw.println(obj.toString());


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
