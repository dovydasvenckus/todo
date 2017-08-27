package com.dovydasvenckus.todo.utils.db.init

import com.dovydasvenckus.jdbc.SqlFileExecutor
import spock.lang.Specification

import static com.dovydasvenckus.todo.utils.db.DatabaseDriver.H2

class DatabaseInitiatorSpec extends Specification {

    SqlFileExecutor sqlFileExecutor = Mock(SqlFileExecutor)

    DatabaseInitiator databaseInitiator = new DatabaseInitiator(H2, sqlFileExecutor)

    def 'should execute correct file'() {
        when:
            databaseInitiator.createTables()

        then:
            1 * sqlFileExecutor.execute('/sql/h2/create.sql')
    }

    def 'should execute seed file'() {
        when:
            databaseInitiator.initSeedData()

        then:
            1 * sqlFileExecutor.execute('/sql/h2/seed.sql')
    }
}
