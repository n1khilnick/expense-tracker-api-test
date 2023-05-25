package com.geekster.expensetracker.repositories;

import com.geekster.expensetracker.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductDao extends JpaRepository<Product,Long> {

    @Query(value = "select * from products where fk_user_user_id = :userId",nativeQuery = true)
    Iterable<Product> findAllProductsByUserId(Long userId);

    @Query(value = "select * from products where fk_user_user_id = :userId and purchase_date =:purchaseDate",nativeQuery = true)
    Iterable<Product> findAllProductsByUserIdAndDate(Long userId, String purchaseDate);
}
