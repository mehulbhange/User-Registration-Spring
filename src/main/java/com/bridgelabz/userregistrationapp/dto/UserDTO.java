package com.bridgelabz.userregistrationapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message = "firstName name Invalid")
    private String firstName;
    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message = "lastName name Invalid")
    private String lastName;
    @Email
    @NotEmpty
    private String email;
    @Size(min = 10, max = 10)
    @NotEmpty
    private String phoneNo;
    @Size(min = 8)
    @NotEmpty
    private String password;
}
