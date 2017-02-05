package com.dovydasvenckus.todo.util;

public interface Service {

    default String getName() {
        return this.getClass().getSimpleName();
    }

}
