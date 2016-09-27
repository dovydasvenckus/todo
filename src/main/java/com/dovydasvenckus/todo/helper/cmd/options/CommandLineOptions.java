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

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getPort() {
        return port;
    }
}
