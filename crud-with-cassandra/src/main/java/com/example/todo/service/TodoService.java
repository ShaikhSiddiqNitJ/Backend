package com.example.todo.service;

import com.example.todo.exception.NotFoundException;
import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    public Todo findById(String id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Todo not found with id: " + id));
    }

    public Todo create(Todo todo) {
        Todo newTodo = new Todo();
        newTodo.setId(UUID.randomUUID().toString());
        newTodo.setTitle(todo.getTitle());
        newTodo.setCompleted(todo.isCompleted());
        return todoRepository.save(newTodo);
    }

    public Todo update(String id, Todo updatedTodo) {
        if (updatedTodo == null) {
            throw new IllegalArgumentException("Updated todo cannot be null");
        }
        Todo existingTodo = findById(id);
        existingTodo.setTitle(updatedTodo.getTitle());
        existingTodo.setCompleted(updatedTodo.isCompleted());
        return todoRepository.save(existingTodo);
    }

    public void delete(String id) {
        Todo existingTodo = findById(id);
        todoRepository.delete(existingTodo);
    }
}
