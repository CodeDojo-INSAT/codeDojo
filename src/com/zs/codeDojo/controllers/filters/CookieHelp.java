package com.zs.codeDojo.controllers.filters;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class CookieHelp {

    public static Cookie getCookie(HttpServletRequest req, String name){
        Cookie targetCookie = null;

        if (req.getCookies() != null){
            for (Cookie cookie : req.getCookies()) {
                if (cookie.getName().equals(name)) {
                    targetCookie = cookie;
                    break;
                }
            }
        }

        return targetCookie;


    }

    public static HttpServletResponse resetCookie(HttpServletResponse res, Cookie cookie){
        Cookie dummyCookie = (Cookie) cookie.clone();
        dummyCookie.setMaxAge(0);
        dummyCookie.setValue(null);
        res.addCookie(dummyCookie);
        res.addCookie(cookie);

        return res;
    }

    public static HttpServletResponse removeCookie(HttpServletRequest req, HttpServletResponse res, String name){
        if (req.getCookies() != null){
            for (Cookie cookie : req.getCookies()) {
                if (cookie.getName().equals(name)) {
                    cookie.setMaxAge(0);
                    cookie.setValue(null);
                    res.addCookie(cookie);
                    res.addCookie(cookie);
                }
            }
        }
        return res;
    }
}