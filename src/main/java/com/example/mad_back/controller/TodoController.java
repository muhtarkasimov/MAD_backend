package com.example.mad_back.controller;

import com.example.mad_back.entity.Todo;
import com.example.mad_back.model.TodoModel;
import com.example.mad_back.repo.TodoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoRepo todoRepo;

    @GetMapping
    public ResponseEntity<?> getTodos() {
        return ResponseEntity.ok(todoRepo.findAll());
    }

    @PostMapping
    public ResponseEntity<?> createTodo(TodoModel todoModel) {
        return ResponseEntity.ok(todoRepo.save(Todo.builder()
                .todo(todoModel.getTodo())
                .isCompleted(todoModel.getIsCompleted())
                .build()));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchTodo(TodoModel todoModel, @PathVariable Long id) {
        Optional<Todo> todo = todoRepo.findById(id);
        if (todo.isPresent()) {
            todo.get().setTodo(todoModel.getTodo() == null ? todo.get().getTodo() : todoModel.getTodo());
            todo.get().setIsCompleted(todoModel.getIsCompleted() == null ? todo.get().getIsCompleted() : todoModel.getIsCompleted());
            return ResponseEntity.ok(todoRepo.save(todo.get()));
        }
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id) {
        todoRepo.deleteById(id);
        return ResponseEntity.ok("OK");
    }
}
