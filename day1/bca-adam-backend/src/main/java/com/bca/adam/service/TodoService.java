package com.bca.adam.service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import com.bca.adam.model.Todo;
import com.bca.adam.repository.TodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    @Autowired
    TodoRepository todoRepo;

    public Todo doneTodo(String id, boolean done, String userId) {
        Todo retVal = new Todo();
        Optional<Todo> _todo = todoRepo.findByIdAndCreatedBy(UUID.fromString(id), userId);
        if (_todo.isPresent()) {
            Todo existTodo = _todo.get();
            existTodo.setDone(done);
            existTodo.setModifiedBy(userId);
            existTodo.setModifiedDate(new Date());
            retVal = todoRepo.save(existTodo);
        } else {
            throw new IllegalArgumentException("data not found");
        }

        return retVal;
    }

}
