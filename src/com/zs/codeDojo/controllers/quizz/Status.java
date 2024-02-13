package com.zs.codeDojo.controllers.quizz;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Status {
    private final String authStatusCode;
    private String authStatusMsg ;

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

        authCodeMsgs.put("410", "Quiz Not Found");

         authCodeMsgs.put("800", "Quiz Created Successfully");
         authCodeMsgs.put("802", "Quiz Deleted Successfully");

    }

    public Status(String code){

         this.authStatusCode = code;
         this.authStatusMsg = authCodeMsgs.get(code);
    }
    public Status(String code, String msg){
        this.authStatusCode = code;
        this.authStatusMsg = msg;
    }
    public Status(String code, Exception e){

        this.authStatusCode = code;
        this.returnedException = e;

    }

    public String toJSON(){
         JSONObject jsonObj = new JSONObject();
         jsonObj.put("status", getStatusCode());
         jsonObj.put("msg", getStatusMsg());
         return jsonObj.toString();
    }

    public String toJSON(String msg){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", getStatusCode());
        jsonObj.put("msg", msg);
        return jsonObj.toString();
    }


    public String getStatusCode() {
        return authStatusCode;
    }
    public String getStatusMsg() {
        return this.authStatusMsg;
    }

    public void setStatusMsg(String msg) {
        this.authStatusMsg = msg;
    }

    public boolean isSucess(){
        return authStatusCode.startsWith("2");
    }

    public Exception getReturnedException(){
        return returnedException;
    }
}
