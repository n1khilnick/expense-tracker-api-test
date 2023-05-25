package com.geekster.expensetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInInput {
    @NotBlank(message = "Email cannot be blank !!")
    @Email(message = "This is not a valid email !!")
    private String userEmail;

    @NotEmpty
    private String userPassword;
}
