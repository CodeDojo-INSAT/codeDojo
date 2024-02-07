package com.zs.codeDojo.controllers.auth;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// @WebServlet("/logout")
public class LogOut extends HttpServlet {
    protected void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        HttpSession ses = req.getSession(false);

        if (ses != null)
            ses.invalidate();

        // res.sendRedirect("/newProj/login");
    }
}