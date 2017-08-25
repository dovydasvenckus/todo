package com.dovydasvenckus.todo.utils.common;

public class StringUtils {

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.trim().isEmpty();
    }
}
