package com.dovydasvenckus.todo.helper;

import com.beust.jcommander.Parameter;

public class CommandLineOptions {
    @Parameter(names = {"--db-host"})
    private String dbHost;

    @Parameter(names = {"--db-user"})
    private String dbUser;

    @Parameter(names = {"--db-pass"})
    private String dbPassword;

    public String getDbHost() {
        return dbHost;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }
}
