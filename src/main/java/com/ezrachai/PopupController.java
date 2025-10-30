package com.ezrachai;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.UUID;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PopupController implements Initializable {
    @FXML
    private TextField todo;

    @FXML
    private TextArea description;

    @FXML
    private ComboBox<String> priority;

    public String[] priorityItems = { "High", "Medium", "Low" };

    @FXML
    private ComboBox<String> category;

    public String[] categoryItems = { "Study", "Personal", "Family" };

    @FXML
    private DatePicker dueDate;

    @FXML
    private Button addButton;

    private TodoItem editingTodo;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        priority.getItems().addAll(priorityItems);
        category.getItems().addAll(categoryItems);

        // Disable user to select past date
        dueDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });

        addButton.disableProperty().bind(
                todo.textProperty().isEmpty()
                        .or(priority.valueProperty().isNull())
                        .or(category.valueProperty().isNull())
                        .or(dueDate.valueProperty().isNull()));
    }

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setTodoData(TodoItem todoItem) {
        editingTodo = todoItem;
        todo.setText(todoItem.getTodo());
        description.setText(todoItem.getDescription());
        priority.setValue(todoItem.getPriority());
        category.setValue(todoItem.getCategory());
        dueDate.setValue(todoItem.getDueDate());

        addButton.setText("Save Todo");
    }

    public void addTodo() {
        if (editingTodo != null) {
            editingTodo.setTodo(todo.getText().trim());
            editingTodo.setDescription(description.getText().trim());
            editingTodo.setCategory(category.getValue());
            editingTodo.setPriority(priority.getValue());
            editingTodo.setDueDate(dueDate.getValue());
            Stage stage = (Stage) todo.getScene().getWindow();
            stage.close();
            return;
        }
        TodoItem newTodoItem = new TodoItem(UUID.randomUUID().toString(), todo.getText().trim(),
                description.getText().trim(), category.getValue(),
                priority.getValue(), dueDate.getValue());
        mainController.addTodo(newTodoItem);
        Stage stage = (Stage) todo.getScene().getWindow();
        stage.close();
    }

}
