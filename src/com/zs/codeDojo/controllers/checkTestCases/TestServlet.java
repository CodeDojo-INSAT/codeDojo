package com.zs.codeDojo.controllers.checkTestCases;

import java.io.IOException;
import java.util.Scanner;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.zs.codeDojo.models.DAO.IOStreams;
// import com.zs.codeDojo.models.DAO.TestCases;
import com.zs.codeDojo.models.checkTestCases.CheckLogic;
import com.zs.codeDojo.models.checkTestCases.Loader;

public class TestServlet extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String javaCode = processInput(request).getString("code");

        Loader loader = new Loader(javaCode);
        Class<?> clazz = loader.compileAndLoadClass();

        JSONObject json = new JSONObject();
        if (clazz == null) {
            json.put("compilationError", loader.getError());
        }
        else {
            // TestCases tc = new LoadTestCases(6).load();
            ServletContext context = getServletContext();
            CheckLogic obj = new CheckLogic(clazz, null, (IOStreams) context.getAttribute("streams"));    

            if (obj.hasError()) {
                json.put("error", obj.getError());
            }
            else {
                json.put("res", obj.getResultWithExecTime());
                if (!obj.isMatched()) {
                    json.put("message", obj.getMessage());
                }
            }
        }
        response.getWriter().write(json.toString());
    }

    private JSONObject processInput(HttpServletRequest req) throws IOException {
        Scanner sc = new Scanner(req.getInputStream());

        String line = "";
        while (sc.hasNextLine()) {
            line += sc.nextLine();
        }
        sc.close();

        return new JSONObject(line);
    }
}
