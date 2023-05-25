package com.geekster.expensetracker.repositories;

import com.geekster.expensetracker.models.AuthToken;
import com.geekster.expensetracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthTokenDao extends JpaRepository<AuthToken,Long> {

    AuthToken findByUser(User user);

    AuthToken findFirstByToken(String token);


}
