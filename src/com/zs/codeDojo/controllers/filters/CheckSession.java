package com.zs.codeDojo.controllers.filters;

import javax.servlet.*;
// import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zs.codeDojo.models.DAO.User;

// import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CheckSession implements Filter {

    public void init (FilterConfig config) throws ServletException {
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) res;

        String uri = httpRequest.getRequestURI();

        if (uri.contains("/auth/") || uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".png") || uri.endsWith(".jpg")) {
            chain.doFilter(httpRequest, httpResponse);
        }
        else {
            if (httpRequest.getSession(false) == null || httpRequest.getSession(false).getAttribute("user") == null) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/auth/login");
            }
            else {
                User user = (User) httpRequest.getSession(false).getAttribute("user");
                if (!user.isVerified()) {
                    httpRequest.getRequestDispatcher("/services/auth/generate_otp.dojo").include(httpRequest, httpResponse);
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/auth/verify");
                }
                else {
                    chain.doFilter(httpRequest, httpResponse);
                }
            }
        }
    }

    public void destroy(){

    }
}