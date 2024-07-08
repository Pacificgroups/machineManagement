package com.wipro.mms.controller;

import com.wipro.mms.model.Role;
import com.wipro.mms.model.User;
import com.wipro.mms.payload.ApiRequest;
import com.wipro.mms.payload.ApiResponse;
import com.wipro.mms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = this.userService.getAllUsers();
        Collections.sort(users);
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username){
        if(!this.userService.isUserExist(username)){
            return new ResponseEntity<>(new ApiResponse("username is not present." , true ) , HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(this.userService.getUserByUsername(username));
    }

    @PutMapping("/{username}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> updateUser(@RequestBody User user , @PathVariable String username){
        if(!this.userService.isUserExist(username)){
            return new ResponseEntity<>(new ApiResponse("username is not present." , false ) , HttpStatus.BAD_REQUEST);
        }
        User updateUser = this.userService.updateUser(user , username);
        return new ResponseEntity<>(updateUser , HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String username){
        if(!this.userService.isUserExist(username)){
            return new ResponseEntity<>(new ApiResponse("username is not present." , true ) , HttpStatus.BAD_REQUEST);
        }
        this.userService.deleteUser(username);
        return new ResponseEntity<>(new ApiResponse("user deleted successfully" , true ) , HttpStatus.OK);
    }

    @PutMapping("/{username}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateRole(@RequestBody ApiRequest<Role> request , @PathVariable String username){
        if(!this.userService.isUserExist(username)){
            return new ResponseEntity<>(new ApiResponse("username is not present." , true ) , HttpStatus.BAD_REQUEST);
        }
        User updateUser = this.userService.updateRole(username , request.getData());
        return new ResponseEntity<>(updateUser , HttpStatus.OK);
    }

    @PutMapping("/{username}/password")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> updatePassword(@RequestBody ApiRequest<String> request , @PathVariable String username){
        if(!this.userService.isUserExist(username)){
            return new ResponseEntity<>(new ApiResponse("username is not present." , true ) , HttpStatus.BAD_REQUEST);
        }
        User updateUser = this.userService.updatePassword(username , request.getData());
        return new ResponseEntity<>(updateUser , HttpStatus.OK);
    }

    @GetMapping("/{username}/isAdmin")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Boolean> isAdmin(@PathVariable String username){
        if(this.userService.isUserExist(username)){
            User user = this.userService.getUserByUsername(username);
            if(user.getRole().equals(Role.ROLE_ADMIN)){
                return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);
    }

    @GetMapping("/search/{username}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<User>> searchUser(@PathVariable String username){
        return new ResponseEntity<>(this.userService.getUsersByUsername(username) , HttpStatus.OK);
    }
}