package com.dovydasvenckus.todo.util.arguments;

public class ArgumentParseException extends RuntimeException {

    public ArgumentParseException() {
        super();
    }

    public ArgumentParseException(String s) {
        super(s);
    }

    public ArgumentParseException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
