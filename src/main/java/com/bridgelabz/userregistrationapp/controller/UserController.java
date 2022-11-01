package com.bridgelabz.userregistrationapp.controller;

import com.bridgelabz.userregistrationapp.dto.LoginDTO;
import com.bridgelabz.userregistrationapp.dto.UserDTO;
import com.bridgelabz.userregistrationapp.entity.User;
import com.bridgelabz.userregistrationapp.service.IUserService;
import com.bridgelabz.userregistrationapp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/user")
    public ResponseEntity<User> addUser(@Valid @RequestBody UserDTO userDTO){
        User user = userService.createUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        return new ResponseEntity<>(userService.getUsers(),HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") long userId){
        return new ResponseEntity<>(userService.getUser(userId),HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody LoginDTO loginDTO){
        String token = userService.userLogin(loginDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/get-user")
    public ResponseEntity<User> getUser(@RequestParam String token){
        User user = userService.getUser(token);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PutMapping("/validate-user/{email}")
    public ResponseEntity<String> validateUser(@PathVariable("email") String email, @RequestParam String otp){
        return new ResponseEntity<>(userService.validateUser(email,otp), HttpStatus.OK);
    }

    @PutMapping("/reset-password-token/{email}")
    public ResponseEntity<String> generateResetPasswordToken(@PathVariable("email") String email){
        String token = userService.generateResetPasswordToken(email);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PutMapping("/reset-password/{token}")
    public ResponseEntity<String> resetPassword(@PathVariable("token") String token, @RequestBody String password){
        return new ResponseEntity<>(userService.resetPassword(token,password),HttpStatus.OK);
    }

}
