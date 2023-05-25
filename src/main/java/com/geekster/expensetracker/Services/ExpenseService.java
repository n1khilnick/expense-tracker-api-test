package com.geekster.expensetracker.Services;

import com.geekster.expensetracker.models.Expense;
import com.geekster.expensetracker.models.User;
import com.geekster.expensetracker.repositories.IExpenseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class ExpenseService {

    @Autowired
    IExpenseDao expenseDao;

    public void addExpense(Expense expense) {

        expenseDao.save(expense);
    }

    public Iterable<Expense> getAllExpensesByUserId(Long userId) {
            return expenseDao.findAllExpensesByUserId(userId);
    }

    public void deleteExpense(String email, String token, Long expenseId) {
        Expense expense = expenseDao.findByExpenseId(expenseId);
        User expenseUser = expense.getUser();
        String userMail = expenseUser.getUserEmail();

        if(userMail.equals(email)){
            expenseDao.deleteById(expenseId);
        }else{
            throw new IllegalStateException("User is not valid !!");
        }

    }

    public void updateExpense(Long expenseId, Double amount) {
           Expense expense = expenseDao.findByExpenseId(expenseId);
            expense.setAmount(amount);
            expenseDao.save(expense);
    }

    public Expense findExpenseById(Long expenseId) {
        return expenseDao.findByExpenseId(expenseId);
    }


}
