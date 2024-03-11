package com.zs.codeDojo.controllers.auth;

import java.io.IOException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

public class UpdateProfile extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        JSONObject json = processRequest(req);
        if (session != null) {
            String firstname = json.getString("fname");
            String lastname = json.getString("lname");
            String passwd = json.getString("passwd");
            String email = json.getString("email");

        }
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