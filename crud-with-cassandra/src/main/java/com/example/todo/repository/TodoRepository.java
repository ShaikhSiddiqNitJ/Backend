package com.example.todo.repository;

import com.example.todo.model.Todo;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface TodoRepository extends CassandraRepository<Todo, String> {
}
