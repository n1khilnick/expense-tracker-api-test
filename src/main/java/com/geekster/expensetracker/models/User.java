package com.geekster.expensetracker.models;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userFullName;

    @Email(message = "This is not a valid email !!")
    private String userEmail;

    @Column(nullable = false,unique = true)
    private String userUserName;

    @NotNull(message = "Password cannot be null !!")
    private String userPassword;


    public User(String userFullName, String userEmail, String userUserName, String userPassword) {
        this.userFullName = userFullName;
        this.userEmail = userEmail;
        this.userUserName = userUserName;
        this.userPassword = userPassword;
    }
}
