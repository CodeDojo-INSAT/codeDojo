package com.zs.codeDojo.controllers.settings;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.zs.codeDojo.models.DAO.User;

@WebServlet("/getUserAcc")
public class GetAccountDetails extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        resp.setContentType("application/json");
        
        JSONObject json = new JSONObject();
        if (session != null) {
            User user = (User) session.getAttribute("user");

            String fname = user.getFirstName();
            String lname = user.getLastName();
            String uname = user.getUsername();
            String email = user.getEmail();

            json.put("fname", fname);
            json.put("lname", lname);
            json.put("uname", uname);
            json.put("email", email);
            json.put("status", true);
        }
        else {
            json.put("status", false);
        }
        resp.getWriter().write(json.toString());
    }
}
