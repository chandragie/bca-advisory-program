package com.bca.adam.repository;

import java.util.List;

import com.bca.adam.model.Todo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, String> {
    List<Todo> findByCreatedBy(String createdBy);
}
