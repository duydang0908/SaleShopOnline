package com.example.duyda.onlinesaleshop.Models;

public class Account {
    private String Name;
    private String Pass;

    public Account() {
    }

    public Account(String name, String pass) {
        Name = name;
        Pass = pass;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }
}
