package com.dovydasvenckus.todo.utils;

public interface Service {

    default String getName() {
        return this.getClass().getSimpleName();
    }

}
