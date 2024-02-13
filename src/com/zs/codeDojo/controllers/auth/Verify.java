package com.zs.codeDojo.controllers.auth;

import javax.servlet.*;
import javax.servlet.http.*;

import com.zs.codeDojo.models.DAO.DBModule;
import com.zs.codeDojo.models.DAO.Response;
import com.zs.codeDojo.models.DAO.User;
import com.zs.codeDojo.models.auth.MailVerifier;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

// @WebServlet("/verify")
public class Verify extends HttpServlet{
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        ServletContext context = getServletContext();

        DBModule db = null;
        try {

            PrintWriter pw = res.getWriter();

            db = (DBModule) context.getAttribute("db");

            HttpSession ses = req.getSession(false);

            User user = db.getUser((String) ses.getAttribute("username"));

            pw.println(user);

            if (!user.isEmpty()){
                String code = db.getVerifyCode(user);
                pw.println(code);
                if (code == null){
                    Response mailRes = MailVerifier.sendMail(user);
                    code = (String) mailRes.getPayload();
                    db.createVerifyCode(user, code);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // RequestDispatcher reqDis = req.getRequestDispatcher("/html/login/verify.jsp");
        // reqDis.forward(req, res);
    }
}
