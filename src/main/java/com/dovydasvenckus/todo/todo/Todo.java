package com.dovydasvenckus.todo.todo;

import java.util.Date;
import java.util.Objects;

public class Todo {

    private Long id;

    private String title;

    private boolean isDone = false;

    private Long todoListId;

    private Date createdAt;

    private Date updatedAt;

    public Todo(String title) {
        this.title = title;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Todo(String title, Long todoListId) {
        this(title);
        this.todoListId = todoListId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getTodoListId() {
        return todoListId;
    }

    public void setTodoListId(Long todoListId) {
        this.todoListId = todoListId;
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
