package com.tri;

import javafx.beans.property.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TodoItem {

    @JsonIgnore
    private final StringProperty id = new SimpleStringProperty();
    @JsonIgnore
    private final StringProperty todo = new SimpleStringProperty("");
    @JsonIgnore
    private final StringProperty description = new SimpleStringProperty("");
    @JsonIgnore
    private final StringProperty category = new SimpleStringProperty("");
    @JsonIgnore
    private final StringProperty priority = new SimpleStringProperty("");
    @JsonIgnore
    private final ObjectProperty<LocalDate> dueDate = new SimpleObjectProperty<>();
    @JsonIgnore
    private final BooleanProperty status = new SimpleBooleanProperty(false);

    public TodoItem() {
    }

    public TodoItem(String id, String todo, String description, String category, String priority, LocalDate dueDate) {
        this.id.set(id);
        this.todo.set(todo);
        this.description.set(description);
        this.category.set(category);
        this.priority.set(priority);
        this.dueDate.set(dueDate);
        this.status.set(false);
    }

    // ID
    @JsonProperty("id")
    public String getId() {
        return id.get();
    }

    @JsonProperty("id")
    public StringProperty idProperty() {
        return id;
    }

    // Todo
    @JsonProperty("todo")
    public String getTodo() {
        return todo.get();
    }

    public void setTodo(String value) {
        todo.set(value);
    }

    @JsonProperty("todo")
    public StringProperty todoProperty() {
        return todo;
    }

    // Description
    @JsonProperty("description")
    public String getDescription() {
        return description.get();
    }

    public void setDescription(String value) {
        description.set(value);
    }

    @JsonProperty("description")
    public StringProperty descriptionProperty() {
        return description;
    }

    // Category
    @JsonProperty("category")
    public String getCategory() {
        return category.get();
    }

    public void setCategory(String value) {
        category.set(value);
    }

    @JsonProperty("category")
    public StringProperty categoryProperty() {
        return category;
    }

    // Priority
    @JsonProperty("priority")
    public String getPriority() {
        return priority.get();
    }

    public void setPriority(String value) {
        priority.set(value);
    }

    @JsonProperty("priority")
    public StringProperty priorityProperty() {
        return priority;
    }

    // DueDate
    @JsonProperty("duedate")
    public LocalDate getDueDate() {
        return dueDate.get();
    }

    public void setDueDate(LocalDate value) {
        if (value.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Due date cannot be in the past");
        }
        dueDate.set(value);
    }

    @JsonProperty("duedate")
    public ObjectProperty<LocalDate> dueDateProperty() {
        return dueDate;
    }

    // Status

    @JsonProperty("status")
    public boolean isStatus() {
        return status.get();
    }

    public void setStatus(boolean value) {
        status.set(value);
    }

    @JsonProperty("status")
    public BooleanProperty statusProperty() {
        return status;
    }

}