package com.zs.codeDojo.models.DAO;

public class User {

    private final String username;

    private String email;
    private final String password;
    private String firstName = null;
    private String lastName = null;

    private boolean verified;

    @Override
    public String toString() {
        return getUsername() + getEmail() + getFirstName() + getLastName();
    }

    public User(){
        this.username = null;
        this.password = null;
    }
    public User(String username, String password){
        this.username = username;
        this.password = password;

    }

    public User(String username, String email, String password, String firstName, String lastName, boolean verified){
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.verified = verified;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isEmpty(){
        return username == null && password == null;
    }

    public String getEmail() {
        return email;
    }

    public boolean isVerified() {
        return verified;
    }
}