package com.dovydasvenckus.todo;

import com.dovydasvenckus.todo.utils.arguments.Argument;

public class CommandLineArgs {

    @Argument(name = "--db-url")
    private String dbUrl;

    @Argument(name = "--db-user")
    private String dbUser;

    @Argument(name = "--db-pass")
    private String dbPassword;

    @Argument(name = "--port")
    private String port;

    @Argument(name = "--app-user")
    private String user;

    @Argument(name = "--app-pass")
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
