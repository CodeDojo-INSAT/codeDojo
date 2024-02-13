package com.zs.codeDojo.models.DAO;

import org.json.JSONObject;

public class JsonResponse {
    private boolean status;
    private String message;
    private Object data;

    public JsonResponse(boolean status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("status", status ? "success":"failed");
        json.put("message", message);
        json.put("data", data);

        return json.toString();
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
