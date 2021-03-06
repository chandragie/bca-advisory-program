package com.bca.adam.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.bca.adam.model.Todo;
import com.bca.adam.repository.TodoRepository;
import com.bca.adam.service.LoginService;
import com.bca.adam.service.TodoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    TodoRepository todoRepo;

    @Autowired
    TodoService todoService;

    @Autowired
    LoginService loginService;

    @GetMapping("")
    public ResponseEntity<List<Todo>> getTodosByCreator(@RequestParam String userId) {
        try {

            if (null == userId)
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

            List<Todo> todos = new ArrayList<>();

            todoRepo.findByCreatedByOrderByIsDoneAscCreatedDateDesc(userId).forEach(todos::add);

            if (todos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(todos, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<Todo> addTodo(@RequestBody Todo todo) {
        try {
            if (todo.getTitle() == null || null == todo.getCreatedBy())
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            Todo _todo = todoRepo.save(new Todo(todo.getTitle(), todo.getCreatedBy()));
            return new ResponseEntity<>(_todo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<Todo> modifyTodo(@RequestBody HashMap<String, String> body) {
        try {
            if (body.get("id") == null || null == body.get("updatedBy"))
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            Todo _todo = todoService.modifyTodo(body.get("id"), body.get("done").equalsIgnoreCase("true") ? true : false,
                    body.get("updatedBy"));
            return new ResponseEntity<>(_todo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
