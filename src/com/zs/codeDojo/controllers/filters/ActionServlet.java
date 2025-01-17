package com.zs.codeDojo.controllers.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionServlet implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = ((HttpServletRequest) request);
        HttpServletResponse httpResponse = ((HttpServletResponse) response);

        httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpResponse.setHeader("Pragma", "no-cache");
        httpResponse.setHeader("Expires", "0");
        httpResponse.setHeader("Access-Control-Allow-Origin", "*"); // Allow requests from any origin
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS"); // Allow specified HTTP methods
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization"); // Allow specified headers
        

        String uri = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        System.out.println("uri: " + uri);

        if (uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".png")) {

            chain.doFilter(httpRequest, httpResponse);
            return;
        }

        String cookieName = "reqEndpoint";
        Cookie endpointCookie = CookieHelp.getCookie(httpRequest, cookieName);

        if (uri.contains("/u/")) {
            if (endpointCookie == null) {
                endpointCookie = new Cookie(cookieName, uri);
                httpResponse.addCookie(endpointCookie);
            } else {
                endpointCookie.setValue(uri);
                CookieHelp.resetCookie(httpResponse, endpointCookie);
            }

            uri = "/app_frame";
        }

        // if (!uri.equals("/app_frame")) {
        //     // httpResponse.setContentType("text/plain");
        // }

        switch (uri) {
            case "/app_frame":
                request.getRequestDispatcher("/WEB-INF/views/sidebar.html").include(httpRequest, httpResponse);
                break;
            case "/":
                httpResponse.sendRedirect("/codeDojo/u/dashboard");
                break;
            case "/auth/login":
                request.getRequestDispatcher("/WEB-INF/views/login.html").include(request, response);
                break;
            case "/views/dashboard":
                request.getRequestDispatcher("/WEB-INF/views/dashboard.html").include(request, response);
                break;
            case "/auth/verify":
                request.getRequestDispatcher("/WEB-INF/views/verify.html").include(httpRequest, response);
                break;
            case "/views/quiz":
                request.getRequestDispatcher("/WEB-INF/views/quiz.html").include(httpRequest, response);
                break;
            case "/views/course":
                request.getRequestDispatcher("/WEB-INF/views/course_list.html").include(httpRequest, httpResponse);
                break;
            case "/views/daily_question":
                request.getRequestDispatcher("/WEB-INF/views/daily_question.html").include(httpRequest, httpResponse);
                break;
            case "/views/course/editor":
                request.getRequestDispatcher("/WEB-INF/views/course.html").include(httpRequest, httpResponse);
                break;
            case "/views/settings":
                request.getRequestDispatcher("/WEB-INF/views/settings.html").include(httpRequest, httpResponse);
                break;
            case "/views/editor":
                request.getRequestDispatcher("/WEB-INF/views/editor.html").include(httpRequest, httpResponse);
                break;
            case "/views/show_today_question":
                request.getRequestDispatcher("/WEB-INF/views/dq.html").include(httpRequest, httpResponse);
                break;
            case "/views/no_dq_ques":
                request.getRequestDispatcher("/WEB-INF/views/dq_no_ques.html").include(httpRequest, httpResponse);
                break;
            default:
                chain.doFilter(httpRequest, httpResponse);
                break;
        }
    }

    @Override
    public void destroy() {
    }

}
