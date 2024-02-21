package com.zs.codeDojo.controllers.auth;

import org.json.*;

import com.zs.codeDojo.models.DAO.DBModule;
import com.zs.codeDojo.models.DAO.User;
import com.zs.codeDojo.models.auth.AuthStatus;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.http.Cookie;

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

                // User user = new User(resolvedUser.getUsername(), resolvedUser.getEmail(), resolvedUser.getFirstName(), resolvedUser.getLastName(), true);
                session.setAttribute("user", resolvedUser);
                res.addCookie(setCookie(resolvedUser));
                
                if (!resolvedUser.isVerified()){
                    pw.println("false");
                }
                else {
                    pw.println("true");
                }
            } else if (!status.getAuthStatusCode().equals("406")) {
                pw.println(status.getAuthStatusMsg());
            }   
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Cookie setCookie(User user) {
        Cookie cookie = new Cookie("name", user.getFirstName());

        cookie.setMaxAge(60 * 60 * 24);
        cookie.setPath("/");
        cookie.setSecure(true);        
        cookie.setHttpOnly(true);
        return cookie;
    }
}
