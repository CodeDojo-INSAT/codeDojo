package com.zs.codeDojo.models.checkTestCases;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetTestCode extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Scanner sc = new Scanner(new File("/opt/apache/webapps/checkTC/Java/Test.java"));
        String code = "";
        while (sc.hasNextLine()) {
            code += sc.nextLine() + "\n";
        }

        sc.close();

        resp.getWriter().print(code);
    }
}
