package com.wipro.mms.service.impl;

import com.wipro.mms.exception.ResourceNotFoundException;
import com.wipro.mms.model.Role;
import com.wipro.mms.model.User;
import com.wipro.mms.repository.UserRepository;
import com.wipro.mms.service.UserService;
import com.wipro.mms.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(User user) {
        String hashPwd = passwordEncoder.encode(user.getPassword());
        user.setId(AppUtils.generateRandomUserId());
        user.setPassword(hashPwd);
        user.setRole(Role.ROLE_USER);
        user.setCreateDt(String.valueOf(new Date(System.currentTimeMillis())));
        user.setUpdatedDt(String.valueOf(new Date(System.currentTimeMillis())));
        return this.userRepository.save(user);
    }

    @Override
    public User updateUser(User user, String username) {
        User savedUser = this.userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User" , "username" , username));
        if(user.getUsername() != null) savedUser.setUsername(user.getUsername());
        if(user.getEmail() != null) savedUser.setEmail(user.getEmail());
        if(user.getFirstName() != null) savedUser.setFirstName(user.getFirstName());
        if(user.getLastName() != null) savedUser.setLastName(user.getLastName());
        savedUser.setUpdatedDt(String.valueOf(new Date(System.currentTimeMillis())));
        return this.userRepository.save(savedUser);

    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User","username",username));
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public void deleteUser(String username) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User" , "username" , username));
        this.userRepository.delete(user);
    }

    @Override
    public boolean isUserExist(String username) {
        Optional<User> user = this.userRepository.findByUsername(username);
        return user.isPresent();
    }

    @Override
    public User updateRole(String username, Role role) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User" , "username" , username));
        user.setRole(role);
        user.setUpdatedDt(String.valueOf(new Date(System.currentTimeMillis())));
        return this.userRepository.save(user);
    }

    @Override
    public User updatePassword(String username, String password) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User" , "username" , username));
        user.setPassword(this.passwordEncoder.encode(password));
        user.setUpdatedDt(String.valueOf(new Date(System.currentTimeMillis())));
        return this.userRepository.save(user);

    }

    @Override
    public List<User> getUsersByUsername(String username) {
        Optional<List<User>> allUsers = this.userRepository.findByUsernameContaining(username);
        List<User> users = new ArrayList<>();
        if(allUsers.isPresent()) users = allUsers.get();
        return users;

    }
}
