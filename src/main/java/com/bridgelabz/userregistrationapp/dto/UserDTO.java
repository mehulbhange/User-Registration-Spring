package com.bridgelabz.userregistrationapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @Size(min = 3)
    private String name;
    @Email
    private String email;
    @Size(min = 10, max = 10)
    private String phoneNo;
    @Size(min = 8)
    private String password;
}
