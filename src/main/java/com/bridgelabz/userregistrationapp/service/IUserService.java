package com.bridgelabz.userregistrationapp.service;

import com.bridgelabz.userregistrationapp.dto.LoginDTO;
import com.bridgelabz.userregistrationapp.dto.UserDTO;
import com.bridgelabz.userregistrationapp.entity.User;

import java.util.List;

public interface IUserService {
    User createUser(UserDTO userDTO);
    List<User> getUsers();
    User getUser(long userId);
    String userLogin(LoginDTO loginDTO);
    User getUser(String token);

    String validateUser(String email, String otp);

    String resetPassword(String token, String password);

    String generateResetPasswordToken(String email);
}
