package com.bca.adam.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.bca.adam.model.Login;
import com.bca.adam.model.User;
import com.bca.adam.repository.LoginRepository;
import com.bca.adam.service.LoginService;
import com.bca.adam.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.MalformedJwtException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/sign")
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    LoginService loginService;

    @Autowired
    LoginRepository loginRepo;

    @PostMapping("/in")
    @Transactional
    public ResponseEntity<String> signin(@RequestBody User user) {
        String jwt = null;
        try {
            if (null != user.getUsername() && null != user.getPassword()) {
                String userId = userService.getUserIdByUsernameAndPassword(user.getUsername(), user.getPassword());
                if (null != userId) {
                    // user and password found, allow login
                    Login _login = loginRepo.save(new Login(userId)); // insert login data
                    return new ResponseEntity<>(_login.getSessionid(), HttpStatus.OK);

                } else {
                    return new ResponseEntity<>("User not found", HttpStatus.OK); // user not found
                }

            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // @PostMapping("/logout")
    @PostMapping("/out")
    @Transactional
    public ResponseEntity<String> signout(@RequestBody HashMap<String, String> body, HttpServletRequest req) {

        try {
            String username = body.get("username");

            if (null == username)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


            loginRepo.logout(username.toString());

            return new ResponseEntity<>(username + " has successfully logged out", HttpStatus.OK);

        } catch (MalformedJwtException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
