package com.zs.codeDojo.controllers.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zs.codeDojo.models.DAO.User;

@WebServlet("/services/auth/get_name.dojo")
public class GetName extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        User user = (User) session.getAttribute("user");
        if (user != null) {
            String name = user.getFirstName();

            response.getWriter().write(name);
        }
    }
}
