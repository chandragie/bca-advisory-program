package com.bca.adam.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bca.adam.model.Todo;
import com.bca.adam.repository.TodoRepository;
import com.bca.adam.service.LoginService;
import com.bca.adam.util.JWTTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    TodoRepository bookRepo;

    @Autowired
    LoginService loginService;

    @GetMapping("")
    public ResponseEntity<List<Todo>> getTodosByCreator(HttpServletRequest req) {
        try {

            String userId = loginService
                    .extractUserIdFromValidJWT(JWTTokenizer.validateJWT(req.getHeader("Authorization")));
            if (null == userId)
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

            List<Todo> todos = new ArrayList<>();

            bookRepo.findByCreatedBy(userId).forEach(todos::add);

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
    public ResponseEntity<Todo> addTodo(@RequestBody Todo todo, HttpServletRequest req) {
        try {
            String userId = loginService
                    .extractUserIdFromValidJWT(JWTTokenizer.validateJWT(req.getHeader("Authorization")));
            if (null == userId)
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

            if (todo.getTitle() == null)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            Todo _book = bookRepo.save(new Todo(todo.getTitle(), userId));
            return new ResponseEntity<>(_book, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
