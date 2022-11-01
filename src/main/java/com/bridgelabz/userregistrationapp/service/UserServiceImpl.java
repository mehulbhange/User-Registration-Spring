package com.bridgelabz.userregistrationapp.service;

import com.bridgelabz.userregistrationapp.dto.LoginDTO;
import com.bridgelabz.userregistrationapp.dto.PasswordDTO;
import com.bridgelabz.userregistrationapp.dto.UserDTO;
import com.bridgelabz.userregistrationapp.entity.User;
import com.bridgelabz.userregistrationapp.exception.InvalidLoginCredentialsException;
import com.bridgelabz.userregistrationapp.exception.UserNotFoundException;
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

    @Autowired
    private EmailService emailService;

    @Override
    public User createUser(UserDTO userDTO) {
        String encryptedPassword = bCryptPasswordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encryptedPassword);
        User user = modelMapper.map(userDTO, User.class);
        int otp = (int)(Math.random() * (99999 - 10000 + 1) + 10000);
        user.setOtp(String.valueOf(otp));
        user.setVerified(false);

        User storedUser = userRepository.save(user);
        if (storedUser != null){
            String email = user.getEmail();
            String body = "Welcome "+ user.getFirstName()+" "+user.getLastName()+" \n Your OTP for verification is : "+ otp;
            String subject = "New user registration verification";
            emailService.sendSimpleMail(email,subject,body);
        }
        return storedUser;
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

    @Override
    public String validateUser(String email, String otp) {
        User user = userRepository.findUserByEmail(email);
        if (user != null){
            if (user.getOtp().equals(otp)){
                user.setVerified(true);
                userRepository.save(user);
                return "User verified !!!";
            }else {
                return "Not verified";
            }
        }else {
            throw new UserNotFoundException("User not found with email : "+ email);
        }
    }

    @Override
    public String resetPassword(String token, String password) {
        PasswordDTO passDTO = tokenUtility.decodePasswordResetToken(token);
        System.out.println("password DTO : "+ passDTO.toString());
        User user = userRepository.findUserByEmail(passDTO.getEmail());

        if (user != null && user.getPasswordResetToken().equals(String.valueOf(passDTO.getTokenNo())) ){

            user.setPassword(bCryptPasswordEncoder.encode(password));
            System.out.println("password : "+ password);
            System.out.println("encoded pass : "+ user.getPassword());
            if (userRepository.save(user) != null){
                return "Password reset successfully!!!";
            }else {
                return "Something went wrong !!!";
            }

        }else {
            return "Something went wrong!!!";
        }
    }

    public String generateResetPasswordToken(String email){
        User user = userRepository.findUserByEmail(email);
        if (user != null){
            int tokenNo = (int)(Math.random() * (99999 - 10000 + 1) + 10000);
            String token = tokenUtility.passwordResetToken(email, (long) tokenNo);

            user.setPasswordResetToken(String.valueOf(tokenNo));
            User storedUser = userRepository.save(user);

            if (storedUser != null){
                String body = token;
                String subject = "password reset token";
                emailService.sendSimpleMail(email,subject,body);
            }

            return "mail sent!!!";
        }else {
            throw new UserNotFoundException("User not found with email : "+ email);
        }
    }


}
