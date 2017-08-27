package com.dovydasvenckus.todo.auth;

import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.config.ConfigFactory;
import org.pac4j.http.client.direct.DirectBasicAuthClient;
import org.pac4j.sparkjava.DefaultHttpActionAdapter;

public class AuthConfigFactory implements ConfigFactory {
    private AuthService authService;

    public AuthConfigFactory(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public Config build(Object... params) {
        DirectBasicAuthClient directBasicAuthClient = new DirectBasicAuthClient(authService, new DumbProfileCreator());
        Clients clients = new Clients(directBasicAuthClient);

        Config config = new Config(clients);
        config.addAuthorizer("user", new RequireAnyRoleAuthorizer("ROLE_USER"));
        config.setHttpActionAdapter(new DefaultHttpActionAdapter());
        config.addAuthorizer("dumbAuthorizer", new DumbAuthorizer());
        return config;
    }
}
