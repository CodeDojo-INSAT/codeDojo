package com.zs.codeDojo.controllers.admin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/getUploadQuestion")
public class getUploadQuestion extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String content = "";
        BufferedReader reader = new BufferedReader(new FileReader("/opt/apache-tomcat-8.5.50/webapps/admin/upload_ques.html"));
        
        String line;
        while ((line = reader.readLine()) != null) {
            content += line + "\n";
        }

        reader.close();
        PrintWriter writer = resp.getWriter();

        writer.print(content);
        writer.close();
    }
}   
