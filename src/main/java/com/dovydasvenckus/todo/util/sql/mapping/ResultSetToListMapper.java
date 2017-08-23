package com.dovydasvenckus.todo.util.sql.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultSetToListMapper<T> {

    private ResultSetMapper<T> resultSetMapper;

    public ResultSetToListMapper(ResultSetMapper<T> resultSetMapper) {
        this.resultSetMapper = resultSetMapper;
    }

    public List<T> getListOfResults(ResultSet resultSet) throws SQLException, MappingException {
        List<T> list = new ArrayList<>();

        while (resultSet.next()) {
            list.add(resultSetMapper.map(resultSet));
        }

        return list;
    }

}
