package com.zs.codeDojo.controllers.quizz;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zs.codeDojo.models.DAO.DBModule;
import com.zs.codeDojo.models.DAO.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
// import java.sql.SQLException;


@WebServlet("/services/quiz/submitAnswers.dojo")
public class CheckAnswersBulk extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null){
            output.append(line);
        }

        JSONObject payload = new JSONObject(output.toString());
        
        
        String quizID = payload.getString("quizID");
        JSONArray answers =payload.getJSONArray("answers");

        PrintWriter pw = res.getWriter();
        ServletContext context = getServletContext();
        DBModule conn = (DBModule)context.getAttribute("db");
        
        User user = (User) req.getSession().getAttribute("user");

        pw.println(conn.checkAndSubmitAnswers(user.getUsername(), quizID, answers));

    }
}
