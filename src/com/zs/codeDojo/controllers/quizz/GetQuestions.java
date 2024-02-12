package com.zs.codeDojo.controllers.quizz;

// import org.json.JSONArray;
import org.json.JSONObject;

import com.zs.codeDojo.models.DAO.DBModule;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
// import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
// import java.sql.SQLException;


// @WebServlet("/services/quiz/getQuestions.dojo")
public class GetQuestions extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");
        
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET");
        res.setHeader("Access-Control-Allow-Headers", "Content-Type");

//        BufferedReader reader = req.getReader();
//        StringBuilder output = new StringBuilder();
        PrintWriter pw = res.getWriter();
//        String line;
//        while ((line = reader.readLine()) != null){
//            output.append(line);
//        }
//        JSONObject payload = new JSONObject(output.toString());

        String quizID = req.getParameter("quizID");
        ServletContext context = getServletContext();
        DBModule conn = (DBModule)context.getAttribute("db");
        JSONObject resObj = conn.getQuestions(quizID);

        pw.println(resObj.toString());






    }
}
