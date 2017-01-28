package com.dovydasvenckus.todo.auth;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.exception.HttpAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

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
    public void validate(UsernamePasswordCredentials credentials, WebContext context) throws HttpAction {
        if (username.equalsIgnoreCase(credentials.getUsername()) && password.equals(credentials.getPassword())) {
            logger.info("Successful authentication");
        } else {
            throw new CredentialsException("Wrong username or password");
        }
    }
}
