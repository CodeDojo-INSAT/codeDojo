package com.zs.codeDojo.models.auth;

import java.util.HashMap;
import java.util.Map;

public class AuthStatus {
    private final String authStatusCode;

    private Exception returnedException = null;

    @Override
    public String toString() {
        return this.authStatusCode;
    }

    public static Map<String, String> authCodeMsgs;
     {
        authCodeMsgs = new HashMap<>();
        authCodeMsgs.put("200", "Login was Successful");
        authCodeMsgs.put("201", "Signup was Successful");
        authCodeMsgs.put("203", "Password Change was Successful");
        authCodeMsgs.put("204", "Verification mail Sent Successfully");
        authCodeMsgs.put("205", "Email ID Verified");


        authCodeMsgs.put("400", "Incorrect Password");
        authCodeMsgs.put("401", "Username taken");
        authCodeMsgs.put("402", "Email ID already registered");
        authCodeMsgs.put("403", "Password Change Unsuccessful");
        authCodeMsgs.put("404", "Username Not Found");
        authCodeMsgs.put("405", "Empty User Given");
        authCodeMsgs.put("406", "SQL Error");
        authCodeMsgs.put("407", "Server Error");
        authCodeMsgs.put("408", "Error Sending Verification mail");
        authCodeMsgs.put("409", "Email ID Not Found");
        authCodeMsgs.put("222", "Compilation error occured");
    }

    public AuthStatus(String code){
        this.authStatusCode = code;
    }

    public AuthStatus(String code, Exception e){

        this.authStatusCode = code;
        this.returnedException = e;
    }

    public String getAuthStatusCode() {
        return authStatusCode;
    }
    public String getAuthStatusMsg() {
        return authCodeMsgs.get(authStatusCode);
    }

    public boolean isSucess(){
        return authStatusCode.startsWith("2");
    }

    public Exception getReturnedException(){
        return returnedException;
    }
}
