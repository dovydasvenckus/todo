package com.dovydasvenckus.todo.todo;

import java.util.Objects;
import java.util.UUID;

public class Todo {

    private String uuid = UUID.randomUUID().toString();
    private String description;
    private boolean isDone = false;

    public Todo(String description) {
        this.description = description;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(boolean done) {
        isDone = done;
    }

    @Override
    public boolean equals(Object o) {
        if ((o != null) && (o instanceof Todo)) {
            return uuid.equals(((Todo) o).getUuid());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
