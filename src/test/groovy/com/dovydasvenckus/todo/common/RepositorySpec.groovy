package com.dovydasvenckus.todo.common

import com.dovydasvenckus.todo.helper.db.DatabaseConfig
import com.dovydasvenckus.todo.helper.db.connector.AlwaysInMemoryH2Connector
import com.dovydasvenckus.todo.helper.db.connector.DatabaseConnector
import spock.lang.Specification

import java.sql.Connection

class RepositorySpec extends Specification {

    DatabaseConfig databaseConfig = new DatabaseConfig(null, null, null)

    DatabaseConnector databaseConnector

    Connection connection

    def setup() {
        databaseConnector = new AlwaysInMemoryH2Connector()
        connection = databaseConnector.getInstance(databaseConfig).get()
    }

    def cleanup() {
        connection.close()
    }

}
