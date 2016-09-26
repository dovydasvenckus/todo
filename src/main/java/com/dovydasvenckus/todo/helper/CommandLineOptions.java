package com.dovydasvenckus.todo.helper;

import com.beust.jcommander.Parameter;

public class CommandLineOptions {
    @Parameter(names = {"env"})
    private String env;

    @Parameter(names = {"--db-host"})
    private String dbHost;

    @Parameter(names = {"--db-user"})
    private String dbUser;

    @Parameter(names = {"--db-pass"})
    private String dbPassword;

    @Parameter(names = "--port")
    private String port;

    public String getDbHost() {
        return dbHost;
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

    public String getEnv() {
        return env;
    }
}
