package com.zs.codeDojo.controllers.auth;

import java.io.*;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.json.JSONObject;

import com.zs.codeDojo.models.DAO.User;
import com.zs.codeDojo.models.auth.ProfileOperations;

@WebServlet("/services/UpdateProfilePhoto")
public class UpdateProfilePhoto extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        

        String content = processRequest(request).getString("content");

        User user = (User) request.getSession(false).getAttribute("user");
        String username = user.getUsername();

        ProfileOperations profile = new ProfileOperations();
        boolean success = profile.updateProfileImage("/opt/apache/webapps/codeDojo/img/userprofiles/" + username + ".png", content, user);

        if (success) {
            response.getWriter().write("Profile image updated successfully.");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update profile image.");
        }
    }

    private JSONObject processRequest(HttpServletRequest req) throws IOException {
        Scanner sc = new Scanner(req.getInputStream());

        String content = "";
        while (sc.hasNextLine()) {
            content += sc.nextLine() + "\n";
        }
        sc.close();

        return new JSONObject(content);
    }
}
