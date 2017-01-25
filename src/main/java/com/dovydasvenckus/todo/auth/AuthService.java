package com.dovydasvenckus.todo.auth;

import com.dovydasvenckus.todo.helper.auth.UsernamePasswordPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

public class AuthService {
    private final static Logger logger = LoggerFactory.getLogger(AuthService.class);
    private String username;
    private String password;

    public AuthService(String username, String password) throws IllegalArgumentException {
        checkArgument(!isNullOrEmpty(username), "Username can't be empty");
        checkArgument(!isNullOrEmpty(password), "Password can't be empty");
        this.username = username;
        this.password = password;
    }

    public boolean isAuthorized(Request request) {
        try {
            UsernamePasswordPair credentials = BasicAuthHeaderDecoder.decode(request.headers("Authorization"));
            return username.equalsIgnoreCase(credentials.getUsername()) && password.equals(credentials.getPassword());
        } catch (RuntimeException ex) {
            logger.info("User failed to log in due to exception {}", ex.toString());
            return false;
        }
    }
}
