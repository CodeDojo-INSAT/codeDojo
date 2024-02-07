package com.zs.codeDojo.properties;

import java.util.Properties;

public class Settings {
    private final String username;
    private final String appPassword;

    private String isAuth = "true";
    private String isTls = "true";
    private String host = "smtp.gmail.com";
    private String port = "587";

    public void setIsAuth(String isAuth) {
        this.isAuth = isAuth;
    }

    public void setIsTls(String isTls) {
        this.isTls = isTls;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    private Properties properties;

    public Settings(String username, String password) {
        this.username = username;
        this.appPassword = password;
        createProperties();
    }

    public String getUsername() {
        return username;
    }

    public String getAppPassword() {
        return appPassword;
    }

    private void createProperties() {
        properties = new Properties();

        properties.put("mail.smtp.auth", isAuth);
        properties.put("mail.smtp.starttls.enable", isTls);
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
    }

    public Properties getProperties() {
        return properties;
    }
}