package com.bridgelabz.userregistrationapp.service;

import com.bridgelabz.userregistrationapp.dto.LoginDTO;
import com.bridgelabz.userregistrationapp.dto.UserDTO;
import com.bridgelabz.userregistrationapp.entity.User;
import com.bridgelabz.userregistrationapp.exception.InvalidLoginCredentialsException;
import com.bridgelabz.userregistrationapp.repository.UserRepository;
import com.bridgelabz.userregistrationapp.utility.TokenUtility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TokenUtility tokenUtility;

    @Override
    public User createUser(UserDTO userDTO) {
        String encryptedPassword = bCryptPasswordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encryptedPassword);
        User user = modelMapper.map(userDTO, User.class);
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(long userId) {
        return null;
    }

    public String userLogin(LoginDTO loginDTO){
        User user = userRepository.findUserByEmail(loginDTO.getEmail());
        String token = null;
        if (user != null){
            if (bCryptPasswordEncoder.matches(loginDTO.getPassword(),user.getPassword())){
                token = tokenUtility.createToken(user.getUserId());
            }else {
                throw new InvalidLoginCredentialsException("Invalid login credentials");
            }
        }else {
            throw new InvalidLoginCredentialsException("Invalid login credentials");
        }
        //String token = tokenUtility.createToken(user.getUserId());
        return token;
    }
    public User getUser(String token){
        Long id = tokenUtility.decodeToken(token);
        return userRepository.findById(id).orElse(null);
    }
}
