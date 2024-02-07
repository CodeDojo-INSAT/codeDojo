package com.zs.codeDojo.controllers.auth;

import org.json.*;

import com.zs.codeDojo.models.DAO.DBModule;
import com.zs.codeDojo.models.DAO.User;
import com.zs.codeDojo.models.auth.AuthStatus;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;

// @WebServlet("/signupSubmit")
public class Signup extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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
        String username = jsonObject.getString("un");
        String password = jsonObject.getString("ps");
        String email = jsonObject.getString("em");
        String firstname = jsonObject.getString("fn");
        String lastname = jsonObject.getString("ln");


        User user = new User(username, email, password, firstname, lastname, false);
        DBModule db = null;
        try {
            db = (DBModule) context.getAttribute("db");
            AuthStatus status = db.createUser(user);
            if(status.isSucess() ){
                pw.print("true");
            }
            else if (!status.getAuthStatusCode().equals("406")){
                pw.print(status.getAuthStatusMsg());
            }else {
                pw.print(status.getAuthStatusMsg());
                StringWriter sw = new StringWriter();
                PrintWriter err = new PrintWriter(sw);
                status.getReturnedException().printStackTrace(err);
                String sStackTrace = sw.toString();
                System.out.println("\n\n[ERROR FACED IN SIGNUP MODULE]\n");
                System.out.println(sStackTrace + "\n\n");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
