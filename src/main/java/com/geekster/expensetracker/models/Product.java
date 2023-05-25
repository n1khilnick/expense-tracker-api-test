package com.geekster.expensetracker.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotNull(message = "You should provide the product title !!")
    private  String productTitle;

    private  String description;

    @NotNull(message = "Price cannot be null !!")
    private  String price;

    @NotNull(message = "Please enter the purchase date !!")
    private LocalDate purchaseDate;

    @ManyToOne
    @JoinColumn(name = "fk_user_userId")
    private User user;



}
