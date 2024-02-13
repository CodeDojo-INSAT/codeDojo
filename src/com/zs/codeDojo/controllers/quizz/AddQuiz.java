package com.zs.codeDojo.controllers.quizz;

import org.json.JSONObject;

import com.zs.codeDojo.models.DAO.DBModule;
import com.zs.codeDojo.models.DAO.QuizExternObject;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
// import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
// import java.sql.SQLException;

// @WebServlet("/services/quiz/addQuiz.dojo")

public class AddQuiz extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {


        res.setContentType("application/json");

        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET");
        res.setHeader("Access-Control-Allow-Headers", "Content-Type");

        BufferedReader reader = req.getReader();
        StringBuilder output = new StringBuilder();
        PrintWriter pw = res.getWriter();
        String line;
        while ((line = reader.readLine()) != null){
            output.append(line);
        }

        JSONObject payload = new JSONObject(output.toString());
        QuizExternObject obj = new QuizExternObject(payload);
        
        ServletContext context = getServletContext();
        DBModule conn = (DBModule)context.getAttribute("db");
        
        Status status = conn.createQuiz(obj);
        pw.println(status.toJSON());



    }
}
