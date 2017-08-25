package com.dovydasvenckus.todo.utils.sql.common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JDBCUtils {

    public static Optional<Long> extractGeneratedId(ResultSet generatedKeys) throws SQLException {
        if (generatedKeys.next()) {
            return Optional.of(generatedKeys.getLong(1));
        }

        return Optional.empty();
    }
}
