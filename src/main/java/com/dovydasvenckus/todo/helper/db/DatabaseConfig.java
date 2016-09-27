package com.dovydasvenckus.todo.helper.db;

import java.util.Optional;

public class DatabaseConfig {
    private Optional<String> url;
    private Optional<String> username;
    private Optional<String> password;

    public DatabaseConfig(String url, String username, String password) {
        this.url = Optional.ofNullable(url);
        this.username = Optional.ofNullable(username);
        this.password = Optional.ofNullable(password);
    }

    public Optional<String> getUrl() {
        return url;
    }

    public Optional<String> getUsername() {
        return username;
    }

    public Optional<String> getPassword() {
        return password;
    }
}
