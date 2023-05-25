package com.geekster.expensetracker.repositories;

import com.geekster.expensetracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDao extends JpaRepository<User,Integer> {

    User findFirstByUserEmail(String userEmail);

}
