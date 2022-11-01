package com.bridgelabz.userregistrationapp.entity;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String phoneNo;
    private String password;
    private String otp;
    private Boolean verified;
    private String passwordResetToken;

}
