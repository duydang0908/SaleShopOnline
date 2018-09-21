package com.example.duyda.onlinesaleshop.Model;

public class Account  {
    private String Name;
    private String Pass;

    public Account(String name, String pass) {
        Name = name;
        Pass = pass;
    }

    public Account() {
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
