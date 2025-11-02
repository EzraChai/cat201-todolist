package com.ezrachai;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TodoStore {
    private static final File FILE = new File("todos.json");
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void save(List<TodoItem> todoItems) {
        try {
            mapper.registerModule(new JavaTimeModule());
            mapper.writeValue(FILE, todoItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<TodoItem> load() {
        try {
            mapper.registerModule(new JavaTimeModule());

            if (!FILE.exists()) {
                return List.of();
            }
            return List.of(mapper.readValue(FILE, TodoItem[].class));
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
