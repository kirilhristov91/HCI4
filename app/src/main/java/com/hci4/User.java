package com.hci4;


public class User {

    int id;
    private String username;
    private String password;
    int loggedIn;
    int consumption;

    public User(String username, String password, int consumption){
        this.username = username;
        this.password = password;
        this.consumption = consumption;
        loggedIn = 0;
    }

    public void setConsumption(int consumption){
        this.consumption = consumption;
    }

    public int getConsumption(){
        return this.consumption;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(int loggedIn) {
        this.loggedIn = loggedIn;
    }
}
