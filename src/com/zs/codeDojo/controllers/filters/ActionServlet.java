package com.zs.codeDojo.controllers.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
// import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class ActionServlet implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
                HttpServletRequest httpRequest = ((HttpServletRequest) request);
                String uri = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
                // System.err.println(uri);

                switch (uri) {
                    case "/auth/login":
                        request.getRequestDispatcher("/WEB-INF/views/login.html").forward(request, response);
                        break;
                    case "/dashboard":
                        // System.err.println("dashboard requested");
                        request.getRequestDispatcher("/WEB-INF/views/dashboard.html").forward(request, response);
                        break;
                    case "/auth/verify":
                        request.getRequestDispatcher("/WEB-INF/views/verify.html").forward(httpRequest, response);
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
