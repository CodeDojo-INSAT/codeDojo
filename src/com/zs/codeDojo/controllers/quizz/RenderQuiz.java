package com.zs.codeDojo.controllers.quizz;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zs.codeDojo.controllers.filters.CookieHelp;


@WebServlet("/u/quiz/*")
public class RenderQuiz extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] uriPath = req.getRequestURI().split("/");
        String quizID = uriPath[uriPath.length - 1];
        Cookie quizCookie = new Cookie("quizID", quizID);
        // CookieHelp.removeCookie(req, resp, "quizID");
        CookieHelp.resetCookie(resp, quizCookie);

        resp.addCookie(quizCookie);

        req.getRequestDispatcher("/WEB-INF/views/quizzes.html").include(req, resp);

    }
}
