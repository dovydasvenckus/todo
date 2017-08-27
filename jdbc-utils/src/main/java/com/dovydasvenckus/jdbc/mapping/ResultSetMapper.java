package com.dovydasvenckus.jdbc.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ResultSetMapper<T> {

    public final T map(ResultSet resultSet) throws MappingException {
        try {
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
            }

            return createEntity(resultSet);
        } catch (SQLException ex) {
            throw new MappingException("Failed while mapping fields", ex);
        }
    }


    protected abstract T createEntity(ResultSet resultSet) throws SQLException;
}
