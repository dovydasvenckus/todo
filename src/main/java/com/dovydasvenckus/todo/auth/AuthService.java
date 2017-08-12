package com.dovydasvenckus.todo.auth;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.exception.HttpAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.dovydasvenckus.todo.util.common.Preconditions.checkArgument;
import static com.dovydasvenckus.todo.util.common.StringUtils.isNullOrEmpty;

public class AuthService implements Authenticator<UsernamePasswordCredentials> {

    private final static Logger logger = LoggerFactory.getLogger(AuthService.class);

    private String username;
    private String password;

    public AuthService(String username, String password) throws IllegalArgumentException {
        checkArgument(!isNullOrEmpty(username), "Username can't be empty");
        checkArgument(!isNullOrEmpty(password), "Password can't be empty");
        this.username = username;
        this.password = password;
    }

    @Override
    public void validate(UsernamePasswordCredentials credentials, WebContext context) throws HttpAction, CredentialsException {
        if (!isCredentialsValid(credentials)) {
            logger.info("Wrong username or password. For user: {}", credentials.getUsername());
            throw new CredentialsException("Wrong username or password");
        }
    }

    private boolean isCredentialsValid(UsernamePasswordCredentials credentials) {
        return username.equalsIgnoreCase(credentials.getUsername())
                && password.equals(credentials.getPassword());
    }
}
