package com.geekster.expensetracker.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;


@Data
@NoArgsConstructor
@Entity
public class AuthToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;

    private String token;

    private LocalDate tokenCreationDate;

    @OneToOne
    @JoinColumn(name = "fk_user_id",nullable = false)
    private User user;

    public AuthToken(User user) {
        this.user = user;
        this.tokenCreationDate = LocalDate.now();
        this.token = UUID.randomUUID().toString();
    }
}
