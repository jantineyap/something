package com.teamanything.goonsquad.database;

/**
 * Created by Jantine on 2/10/2015.
 * User class for storing user email and pass
 * with getter and setter methods
 */
public class User {

    private String email;
    private String pass;
    private String name;

    public User(){

    }

    public User(String email, String name, String pass){
        this.email = email;
        this.pass = pass;
        this.name = name;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPass(){
        return this.pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}
