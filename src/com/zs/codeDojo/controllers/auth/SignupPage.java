package com.zs.codeDojo.controllers.auth;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


// @WebServlet("/signup")
public class SignupPage extends HttpServlet{
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        res.setContentType("text/html");


        RequestDispatcher reqDis = req.getRequestDispatcher("/html/login/signup.html");
        reqDis.forward(req, res);
    }
}
