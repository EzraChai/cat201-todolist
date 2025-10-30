package com.ezrachai;

import javafx.beans.property.*;
import java.time.LocalDate;

public class TodoItem {
    private final StringProperty id;
    private final StringProperty todo;
    private final StringProperty description;
    private final StringProperty category;
    private final StringProperty priority;
    private final ObjectProperty<LocalDate> dueDate;
    private final BooleanProperty status; // <- now a property

    public TodoItem(String id, String todo, String description, String category, String priority, LocalDate dueDate) {
        this.id = new SimpleStringProperty(id);
        this.todo = new SimpleStringProperty(todo);
        this.description = new SimpleStringProperty(description);
        this.category = new SimpleStringProperty(category);
        this.priority = new SimpleStringProperty(priority);
        this.dueDate = new SimpleObjectProperty<>(dueDate);
        this.status = new SimpleBooleanProperty(false); // default false
    }

    // ID
    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    // Todo
    public String getTodo() {
        return todo.get();
    }

    public void setTodo(String value) {
        todo.set(value);
    }

    public StringProperty todoProperty() {
        return todo;
    }

    // Description
    public String getDescription() {
        return description.get();
    }

    public void setDescription(String value) {
        description.set(value);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    // Category
    public String getCategory() {
        return category.get();
    }

    public void setCategory(String value) {
        category.set(value);
    }

    public StringProperty categoryProperty() {
        return category;
    }

    // Priority
    public String getPriority() {
        return priority.get();
    }

    public void setPriority(String value) {
        priority.set(value);
    }

    public StringProperty priorityProperty() {
        return priority;
    }

    // DueDate
    public LocalDate getDueDate() {
        return dueDate.get();
    }

    public void setDueDate(LocalDate value) {
        if (value.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Due date cannot be in the past");
        }
        dueDate.set(value);
    }

    public ObjectProperty<LocalDate> dueDateProperty() {
        return dueDate;
    }

    // Status
    public boolean isStatus() {
        return status.get();
    }

    public void setStatus(boolean value) {
        status.set(value);
    }

    public BooleanProperty statusProperty() {
        return status;
    }
}
