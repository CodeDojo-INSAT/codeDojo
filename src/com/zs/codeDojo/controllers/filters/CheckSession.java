package com.zs.codeDojo.controllers.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zs.codeDojo.models.DAO.User;

import java.io.IOException;

public class CheckSession implements Filter {

    @Override
    public void init (FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) res;

        String uri = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        if (uri.contains("/auth/") || uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".png") || uri.endsWith(".jpg") || uri.endsWith(".svg")) {
            if (uri.equals("/auth/login") && isLoggedIn(httpRequest)) {
                httpResponse.sendRedirect(httpRequest.getContextPath() +  "/u/dashboard");
            }
            chain.doFilter(httpRequest, httpResponse);
        }
        else {
            if (isLoggedIn(httpRequest)) {
                User user = (User) httpRequest.getSession(false).getAttribute("user");
                if (!user.isVerified()) {
                    httpRequest.getRequestDispatcher("/services/auth/generate_otp.dojo").include(httpRequest, httpResponse);
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/auth/verify");
                }
                else {
                    chain.doFilter(httpRequest, httpResponse);
                    
                }
            }
            else {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/auth/login");
            }
        }
    }

    private boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("user") != null;
    }

    @Override
    public void destroy(){
    }
}