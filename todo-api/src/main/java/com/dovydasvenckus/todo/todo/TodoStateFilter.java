package com.dovydasvenckus.todo.todo;

public enum TodoStateFilter {

    ALL, DONE, NOT_DONE;

    TodoStateFilter() {
    }

    public static TodoStateFilter getStateFilter(String doneParam) {

        if (doneParam != null) {
            return Boolean.parseBoolean(doneParam) ? DONE : NOT_DONE;
        }

        return ALL;
    }
}
