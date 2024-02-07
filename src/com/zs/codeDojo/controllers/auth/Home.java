package com.zs.codeDojo.controllers.auth;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


// @WebServlet("/home")
public class Home extends HttpServlet{


    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        res.setContentType("text/html");

        HttpSession session = req.getSession(false);
//        PrintWriter writer = res.getWriter();
        if (session != null) {
            RequestDispatcher rd = req.getRequestDispatcher("/html/login/welcome.html");
            rd.include(req, res);
        }
        else {
            res.sendRedirect("/newProj/login");
        }
//        writer.close();
    }
}
