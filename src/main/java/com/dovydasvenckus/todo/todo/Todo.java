package com.dovydasvenckus.todo.todo;

import java.util.Objects;
import java.util.UUID;

public class Todo {

    private String id = UUID.randomUUID().toString();
    private String title;
    private boolean isDone = false;

    public Todo(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(boolean done) {
        isDone = done;
    }

    public void toggleDone() {
        isDone = !isDone;
    }

    @Override
    public boolean equals(Object o) {
        if ((o != null) && (o instanceof Todo)) {
            return id.equals(((Todo) o).getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
