package com.geekster.expensetracker.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.time.Month;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseId;

    private String expenseName;

    private Double amount;

    @Past(message="Date cannot be in the future")
    private LocalDate createdDate;

    @ManyToOne
    @JoinColumn(name = "fk_user_userId")
    private User user;

}
