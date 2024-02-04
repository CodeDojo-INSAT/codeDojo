package com.zs.codeDojo.controllers.dailyQuestions;

import java.io.IOException;
import java.sql.Connection;
import java.util.Scanner;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.zs.codeDojo.models.DAO.DBModule;
import com.zs.codeDojo.models.DAO.IOStreams;
import com.zs.codeDojo.models.DAO.TestCases;
import com.zs.codeDojo.models.checkTestCases.CheckLogic;
import com.zs.codeDojo.models.checkTestCases.LoadClass;

public class CheckAnswer extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = processRequest(request);
        
        String javaCode = json.getString("code");

        LoadClass loader = new LoadClass("Test", javaCode);
        Class<?> clazz = loader.compileAndLoadClass();

        json.clear();
        ServletContext context = getServletContext();
        DBModule dbModule = new DBModule((Connection) context.getAttribute("conn"));

        TestCases testCases = null;
        if ((testCases = dbModule.getTodayQuestionTestCases()) != null) {
            CheckLogic logicChecker = new CheckLogic(clazz, testCases, (IOStreams) context.getAttribute("streams"));

            json.put("res", logicChecker.getResult());
            if (!logicChecker.isMatched()) {
                json.put("message", logicChecker.getMessage());
            }
        }
        else {
            json.put("error", dbModule.getError());
        }

        response.getWriter().write(json.toString());
    }

    private JSONObject processRequest(HttpServletRequest req) throws IOException {
        Scanner sc = new Scanner(req.getInputStream());

        String content = "";
        while (sc.hasNextLine()) {
            content += sc.nextLine() + "\n";
        }
        sc.close();

        return new JSONObject(content);
    }
}
