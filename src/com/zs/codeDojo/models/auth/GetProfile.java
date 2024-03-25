package com.zs.codeDojo.models.auth;

public class GetProfile {
    private String profileName;
    public GetProfile(String filename) {
        this.profileName = filename;
    }

    public String getProfileName() {
        return this.profileName;
    }    
}
