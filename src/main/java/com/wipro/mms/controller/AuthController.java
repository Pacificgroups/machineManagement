package com.wipro.mms.controller;

import com.wipro.mms.model.User;
import com.wipro.mms.payload.ApiResponse;
import com.wipro.mms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        if(this.userService.isUserExist(user.getUsername())){
            return new ResponseEntity<>(new ApiResponse("username already exists." , false ) , HttpStatus.BAD_REQUEST);
        }
        User createUser = this.userService.registerUser(user);
        return new ResponseEntity<>(createUser , HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(Authentication authentication){
        if(this.userService.isUserExist(authentication.getName())){
            User user = this.userService.getUserByUsername(authentication.getName());
            return new ResponseEntity<>(user , HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse("username is not present." , false ) , HttpStatus.BAD_REQUEST);
    }
}

