package com.zs.codeDojo.controllers.filters;

import java.io.IOException;
import java.net.HttpCookie;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
// import javax.servlet.RequestDispatcher;
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

                String uri = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
                // System.err.println(uri);

                if (uri.contains("/u/")) {
                    Cookie cookie = new Cookie("endPoint", uri);
                    
                    httpResponse.addCookie(cookie);
                    // request.getRequestDispatcher("/dashboard").forward(httpRequest, httpResponse);
                    uri = "/app_frame";
                }
                
                switch (uri) {
                    case "/app_frame":
                        request.getRequestDispatcher("/WEB-INF/views/sidebar.html").forward(httpRequest, httpResponse);
                        break;
                    case "/auth/login":
                        request.getRequestDispatcher("/WEB-INF/views/login.html").forward(request, response);
                        break;
                    case "/views/dashboard":
                        // System.err.println("dashboard requested");
                        request.getRequestDispatcher("/WEB-INF/views/dashboard.html").forward(request, response);
                        break;
                    case "/auth/verify":
                        request.getRequestDispatcher("/WEB-INF/views/verify.html").forward(httpRequest, response);
                        break;
                    case "/views/quiz":
                        request.getRequestDispatcher("/WEB-INF/views/quiz.html").forward(httpRequest, response);
                        break;
                    default:
                        chain.doFilter(request, response);
                        break;
                }
            
                
                // chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
    
}
