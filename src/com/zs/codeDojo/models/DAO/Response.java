package com.zs.codeDojo.models.DAO;

import com.zs.codeDojo.models.auth.AuthStatus;

public class Response {
    private AuthStatus status;
    private Object payload;

    public AuthStatus getStatus() {
        return status;
    }

    public Object getPayload(){
        return payload;
    }

    public Response(Object payload, AuthStatus status){
        this.payload = payload;
        this.status = status;
    }
}