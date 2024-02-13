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
import com.zs.codeDojo.models.DAO.JsonResponse;
// import com.zs.codeDojo.models.DAO.TestCases;
import com.zs.codeDojo.models.checkTestCases.CheckLogic;
import com.zs.codeDojo.models.checkTestCases.Loader;

public class TestServlet extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String javaCode = processInput(request).getString("code");

        Loader loader = new Loader(javaCode);
        Class<?> clazz = loader.compileAndLoadClass();

        JsonResponse jsonResponse = null;
        if (clazz == null) {
            jsonResponse = new JsonResponse(false, "compilation error", loader.getError());
        }
        else {
            ServletContext context = getServletContext();
            CheckLogic obj = new CheckLogic(clazz, null, (IOStreams) context.getAttribute("streams"));    

            if (obj.hasError()) {
                jsonResponse = new JsonResponse(false, "Runtime error occured", obj.getError());
            }
            else {
                JSONObject json = new JSONObject();
                json.put("result", obj.getResult());
                
                if (!obj.isMatched()) {
                    jsonResponse = new JsonResponse(false, "testcases not matched", json);
                }
                else {
                    jsonResponse = new JsonResponse(true, "All testcases passed", json);
                }  
            }
        }
        response.getWriter().print(jsonResponse);
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
