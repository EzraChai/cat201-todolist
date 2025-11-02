package com.ezrachai;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController implements Initializable {

    @FXML
    private TableView<TodoItem> todoListTableView;
    @FXML
    private TableColumn<TodoItem, String> todoColumn;
    @FXML
    private TableColumn<TodoItem, String> descColumn;
    @FXML
    private TableColumn<TodoItem, String> categoryColumn;
    @FXML
    private TableColumn<TodoItem, String> priorityColumn;
    @FXML
    private TableColumn<TodoItem, LocalDate> dueDateColumn;
    @FXML
    private TableColumn<TodoItem, Boolean> statusColumn;

    @FXML
    private TextField searchTextField;

    @FXML
    private DatePicker searchDueDate;

    @FXML
    private ComboBox<String> statusComboBox;

    public String[] status = { "All", "Not Started", "Completed" };

    @FXML
    private ComboBox<String> categoryComboBox;

    public String[] categoryItems = { "", "Study", "Personal", "Family" };

    private ObservableList<TodoItem> todoList = FXCollections.observableArrayList();

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private MenuItem editTaskItem;
    @FXML
    private MenuItem deleteTaskItem;

    public void saveTodos() {
        List<TodoItem> snapshot = new ArrayList<TodoItem>(todoList);
        new Thread(() -> TodoStore.save(snapshot)).start();
    }

    public void editTodo() throws IOException {
        TodoItem selected = todoListTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("assignment1.fxml"));
        Parent root = loader.load();
        PopupController popupController = loader.getController();
        popupController.setMainController(this);
        popupController.setTodoData(selected);

        Stage popupStage = new Stage();
        popupStage.setTitle("Edit Todo");
        popupStage.setScene(new Scene(root));
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.showAndWait();
    }

    public void deleteTodo() {
        TodoItem selected = todoListTableView.getSelectionModel().getSelectedItem();
        todoList.remove(selected);
    }

    public void openPopUp() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("assignment1.fxml"));
        Parent root = loader.load();

        PopupController popupController = loader.getController();
        popupController.setMainController(this);

        Stage popupStage = new Stage();
        popupStage.setTitle("Add Todo");
        popupStage.setScene(new Scene(root));
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.showAndWait();
    }

    public void searchTodo() {
        System.out.println(searchTextField.getText());
    }

    public void addTodo(TodoItem todoItem) {
        todoList.add(0, todoItem);
        todoListTableView.setItems(todoList);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        todoList.addAll(TodoStore.load());
        todoListTableView.setItems(todoList);

        todoColumn.setCellValueFactory(new PropertyValueFactory<>("todo"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        statusComboBox.setValue("All");
        statusComboBox.getItems().addAll(status);
        categoryComboBox.setValue("");
        categoryComboBox.getItems().addAll(categoryItems);

        priorityColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String priority, boolean empty) {
                super.updateItem(priority, empty);
                if (priority == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(priority);
                    setAlignment(Pos.CENTER);
                    switch (priority.toLowerCase()) {
                        case "high":
                            setStyle("-fx-background-color: #ffcccc;");
                            setTextFill(Color.RED);
                            break;
                        case "medium":
                            setStyle("-fx-background-color: #fff2cc;");
                            setTextFill(Color.ORANGE);
                            break;
                        case "low":
                            setStyle("-fx-background-color: #ccffcc;");
                            setTextFill(Color.GREEN);
                            break;
                        default:
                            break;
                    }
                }
            }
        });

        todoListTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            // Enable button if a row is selected
            editTaskItem.setDisable(newSelection == null);
            deleteTaskItem.setDisable(newSelection == null);
            editButton.setDisable(newSelection == null);
            deleteButton.setDisable(newSelection == null);
        });
        statusColumn.setCellFactory(CheckBoxTableCell.forTableColumn(statusColumn));

    }

    public void handleExit() {
        Platform.exit();
    }

    public void handleAbout() {
        new Alert(Alert.AlertType.INFORMATION, "A To-Do List App v1.0").showAndWait();
    }
}
