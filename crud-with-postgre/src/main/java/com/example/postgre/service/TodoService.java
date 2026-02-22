package com.example.postgre.service;

import com.example.postgre.model.Todo;
import com.example.postgre.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public Todo create(Todo todo) {
        return repository.save(todo);
    }

    public List<Todo> getAll() {
        return repository.findAll();
    }

    public Optional<Todo> getById(Long id) {
        return repository.findById(id);
    }

    public Todo update(Long id, Todo todo) {
        todo.setId(id);
        return repository.save(todo);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
