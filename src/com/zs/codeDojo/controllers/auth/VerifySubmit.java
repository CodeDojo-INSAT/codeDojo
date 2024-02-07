package com.zs.codeDojo.controllers.auth;

import javax.servlet.http.*;

import com.zs.codeDojo.models.DAO.DBModule;
import com.zs.codeDojo.models.DAO.User;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


// @WebServlet("/verifySubmit")
public class VerifySubmit extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ServletContext context = getServletContext();

        DBModule db = null;
        try {
            PrintWriter pw = res.getWriter();
            db = (DBModule) context.getAttribute("db");
            HttpSession ses = req.getSession();

            String recCode = req.getParameter("vc");

            User user = db.getUser((String) ses.getAttribute("username"));
            pw.println((String) ses.getAttribute("username"));

            System.err.println(user);
            String genCode = db.getVerifyCode(user);

            if (recCode.equals(genCode)){
                db.setVerified(user);
                pw.println("verified");
                // RequestDispatcher reqDis = req.getRequestDispatcher("/home");
                // reqDis.forward(req, res);
            }else {
                pw.println("Invalid Verification Code ");
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

