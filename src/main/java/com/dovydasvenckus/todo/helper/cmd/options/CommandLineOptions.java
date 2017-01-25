package com.dovydasvenckus.todo.helper.cmd.options;

import com.beust.jcommander.Parameter;

public class CommandLineOptions {

    @Parameter(names = {"--db-url"})
    private String dbUrl;

    @Parameter(names = {"--db-user"})
    private String dbUser;

    @Parameter(names = {"--db-pass"})
    private String dbPassword;

    @Parameter(names = "--port")
    private String port;

    @Parameter(names = "--app-user")
    private String user;

    @Parameter(names = "--app-pass")
    private String password;

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPass() {
        return dbPassword;
    }

    public String getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
