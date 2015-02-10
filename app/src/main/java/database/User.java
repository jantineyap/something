package database;

/**
 * Created by Jantine on 2/10/2015.
 * User class for storing user name and pass
 * with getter and setter methods
 */
public class User {

    private String name;
    private String pass;

    public User(){

    }

    public User(String name, String pass){
        this.name = name;
        this.pass = pass;
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
