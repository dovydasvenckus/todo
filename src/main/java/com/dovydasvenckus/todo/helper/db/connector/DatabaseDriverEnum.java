package com.dovydasvenckus.todo.helper.db.connector;

enum DatabaseDriverEnum {
    H2("h2"),
    POSTGRESQL("postgresql");

    private final String driverName;

    DatabaseDriverEnum(String driverName) {
        this.driverName = driverName;
    }

    @Override
    public String toString() {
        return driverName;
    }

    public boolean isUsingSameDriver(String jdbcUrl) {
        return jdbcUrl.contains("jdbc:" + driverName);
    }

    public DatabaseConnector getConnector() throws ClassNotFoundException {
        switch (this) {
            case H2:
                return new H2Connector();
            case POSTGRESQL:
                return new PostgresqlConnector();
        }
        return null;
    }
}
