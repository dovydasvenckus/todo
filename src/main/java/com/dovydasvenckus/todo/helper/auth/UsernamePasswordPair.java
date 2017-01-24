package com.dovydasvenckus.todo.helper.auth;

public class UsernamePasswordPair {
    private String username;

    private String password;

    public UsernamePasswordPair(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
