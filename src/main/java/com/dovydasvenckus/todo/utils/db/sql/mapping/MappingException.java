package com.dovydasvenckus.todo.utils.db.sql.mapping;

public class MappingException extends Exception {

    public MappingException() {
    }

    public MappingException(String s) {
        super(s);
    }

    public MappingException(String s, Throwable throwable) {
        super(s, throwable);
    }
}