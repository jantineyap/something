package com.teamanything.goonsquad.database;

/**
 * Created by Jantine on 2/10/2015.
 * User class for storing user email and pass
 * with getter and setter methods
 */
public class User {

    private String email;
    private String pass;

    public User(){

    }

    public User(String email, String pass){
        this.email = email;
        this.pass = pass;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPass(){
        return this.pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}
