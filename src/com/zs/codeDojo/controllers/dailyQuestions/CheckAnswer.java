package com.zs.codeDojo.controllers.dailyQuestions;

import java.io.IOException;
import java.util.Scanner;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.zs.codeDojo.models.DAO.DBModule;
import com.zs.codeDojo.models.DAO.IOStreams;
import com.zs.codeDojo.models.DAO.JsonResponse;
import com.zs.codeDojo.models.DAO.TestCases;
import com.zs.codeDojo.models.DAO.User;
import com.zs.codeDojo.models.checkTestCases.CheckLogic;
import com.zs.codeDojo.models.checkTestCases.Loader;

public class CheckAnswer extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JSONObject json = processRequest(request);
        response.setContentType("application/json");

        String javaCode = json.getString("code");
        HttpSession session = request.getSession(false);

        json.clear();

        JsonResponse jsonResponse = null;
        TestCases testCases = null;

        ServletContext context = getServletContext();
        DBModule dbModule = (DBModule) context.getAttribute("db");

        if ((testCases = dbModule.getTodayQuestionTestCases()) != null) {
            Loader loader = new Loader(javaCode);
            Class<?> clazz = loader.compileAndLoadClass();

            if (clazz == null) {
                jsonResponse = new JsonResponse(false, "compilation error occured", loader.getError());
            } else {
                CheckLogic logicChecker = new CheckLogic(clazz, testCases, (IOStreams) context.getAttribute("streams"));

                if (logicChecker.hasError()) {
                    json.put("error", logicChecker.getError());
                    jsonResponse = new JsonResponse(false, "compilation error", json);
                } else {
                    json.put("result", logicChecker.getResult());
                    json.put("sampleTestcase", testCases.getSampleTestCase());

                    if (!logicChecker.isMatched()) {
                        jsonResponse = new JsonResponse(false, "testcases not matched", json);
                    } else {
                        User user = null;
                        int status = 0;
                        if ((user = (User) session.getAttribute("user")) != null) {
                            status = dbModule.updateStreak(user.getUsername());
                        }
                        json.put("streak_update", status == 1);
                        jsonResponse = new JsonResponse(true, "All test cases matched", json);
                    }
                }
            }
        } else {
            jsonResponse = new JsonResponse(false, "can't get today question", null);
        }

        response.getWriter().print(jsonResponse);
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
