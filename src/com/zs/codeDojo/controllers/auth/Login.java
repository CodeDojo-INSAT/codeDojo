package com.zs.codeDojo.controllers.auth;

import org.json.*;

import com.zs.codeDojo.models.DAO.DBModule;
import com.zs.codeDojo.models.DAO.User;
import com.zs.codeDojo.models.auth.AuthStatus;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.SQLException;


// @WebServlet("/loginSubmit")
public class Login extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        ServletContext context = getServletContext();
        PrintWriter pw = res.getWriter();

        BufferedReader reader = req.getReader();
        StringBuilder payload = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null){
            payload.append(line);
        }
        JSONObject jsonObject = new JSONObject(payload.toString());
        String usrnme = jsonObject.getString("un");
        String password = jsonObject.getString("ps");


        System.err.println(usrnme);
        System.err.println(password);
        User usr = new User(usrnme, password);
        try {
            DBModule db = (DBModule) context.getAttribute("db");

            AuthStatus status = db.authenticate(usr);
            if (status.isSucess()){
                HttpSession session = req.getSession();

                User resolvedUser = db.getUser(usr.getUsername());

                if (resolvedUser.isEmpty()){
                    pw.println("Invalid User :(");
                    return;
                }


                session.setAttribute("username", resolvedUser.getUsername());
                session.setAttribute("email", resolvedUser.getEmail());
                session.setAttribute("firstname", resolvedUser.getFirstName());
                session.setAttribute("lastname", resolvedUser.getLastName());

                if (!resolvedUser.isVerified()){
//                    rd = req.getRequestDispatcher("/verify");
                    pw.println("false");
                }
                else {
                    pw.println("true");
//                    rd = req.getRequestDispatcher("/home");
                }

//                rd.forward(req, res);

            } else if (!status.getAuthStatusCode().equals("406")) {
                pw.println(status.getAuthStatusMsg());
            }   
        //    else {
        //            StringWriter sw = new StringWriter();
        //            PrintWriter err = new PrintWriter(sw);
        //            status.getReturnedException().printStackTrace(err);
        //            String sStackTrace = sw.toString(); // stack trace as a string
        //            pw.println(sStackTrace);
        //    }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
