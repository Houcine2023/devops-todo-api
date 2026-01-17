package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/todos")
class TodoController {
    private static final Logger log = LoggerFactory.getLogger(TodoController.class);
    private final List<Todo> todos = Collections.synchronizedList(new ArrayList<>());

    @GetMapping
    public List<Todo> getAll() {
        log.info("Action: Fetching all todo items"); // Observability: Logs
        return todos;
    }

    @PostMapping
    public Todo add(@RequestBody String content) {
        Todo newTodo = new Todo(UUID.randomUUID().toString(), content, false);
        todos.add(newTodo);
        log.info("Action: Created todo with ID: {}", newTodo.id()); // Observability: Logs + Trace context
        return newTodo;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        log.info("Action: Deleting todo ID: {}", id);
        todos.removeIf(t -> t.id().equals(id));
    }
}

// Record reduces boilerplate code significantly
record Todo(String id, String content, boolean completed) {}