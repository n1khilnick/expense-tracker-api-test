package com.geekster.expensetracker.controllers;

import com.geekster.expensetracker.Services.AuthTokenService;
import com.geekster.expensetracker.Services.ExpenseService;
import com.geekster.expensetracker.Services.ProductService;
import com.geekster.expensetracker.Services.UserService;
import com.geekster.expensetracker.dto.SignInInput;
import com.geekster.expensetracker.dto.SignInOutput;
import com.geekster.expensetracker.dto.SignUpOutput;
import com.geekster.expensetracker.models.Expense;
import com.geekster.expensetracker.models.Product;
import com.geekster.expensetracker.models.User;
import com.geekster.expensetracker.repositories.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthTokenService authTokenService;

    @Autowired
    ExpenseService expenseService;

    @Autowired
    ProductService productService;

    @Autowired
    IUserDao userDao;


    @PostMapping(value = "/signup")
    public SignUpOutput signUp(@RequestBody User signUpDto){
        return userService.signUp(signUpDto);
    }

    //sign-in

    @PostMapping(value = "/signin")
    public SignInOutput signIn(@RequestBody SignInInput signInDto){
        return userService.signIn(signInDto);
      
    }


    @PostMapping("/expenses/email/{email}/token/{token}")
    public ResponseEntity<String>addExpenses(@PathVariable String email,@PathVariable String token ,@RequestBody Expense expense){
        HttpStatus status;
        String message;


        if(authTokenService.authenticate(email,token)){
            User user = authTokenService.findUserByToken(token);
            expense.setUser(user);
            expenseService.addExpense(expense);
//            user.setExpense(p);
            message = "Your total expenses is : "+expense.getAmount();
            status = HttpStatus.OK;
        }
        else {
            message = "Invalid User !!";
            status = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<String>(message , status);
    }


    @GetMapping("/expenses/email/{email}/token/{token}")
    public ResponseEntity<Iterable<Expense>> checkExpenses(@PathVariable String email,@PathVariable String token){
        HttpStatus status;
        String msg = "";
        Iterable<Expense> myExpenses = new ArrayList<>();
        if(authTokenService.authenticate(email,token))
        {
            User user =  authTokenService.findUserByToken(token);
            myExpenses = expenseService.getAllExpensesByUserId(Long.valueOf(user.getUserId()));
            status = HttpStatus.OK;
        }
        else
        {
            msg = "Invalid user";
            status = HttpStatus.FORBIDDEN;
        }

        return new ResponseEntity<>(myExpenses, status);
    }




    @PutMapping("/expenses/expenseId/{expenseId}/amount/{amount}")
    public ResponseEntity<String> updateUser(@RequestParam String email , @RequestParam String token , @PathVariable Long expenseId,@PathVariable Double amount){
        HttpStatus status;
        String msg=null;

        if(authTokenService.authenticate(email,token))
        {
            try{
                Expense expense = expenseService.findExpenseById(expenseId);
                expenseService.updateExpense(expenseId,amount);
                status = HttpStatus.OK;
                msg = "Expense with id "+expenseId+" updated successfully  \n" +
                        "Your total expense is :"+expense.getAmount();
            }catch (Exception e){
                msg = "Enter valid information";
                status = HttpStatus.BAD_REQUEST;
                e.printStackTrace();
            }

        }
        else
        {
            msg = "Invalid User";
            status = HttpStatus.FORBIDDEN;
        }

        return new ResponseEntity<String>(msg , status);
    }



    @DeleteMapping("/expenses/email/{email}/token/{token}/expenseId/{expenseId}")
    public ResponseEntity<String> deleteExpense(@PathVariable String email,@PathVariable String token,@PathVariable Long expenseId){
        HttpStatus status;
        String message = null;
        if(authTokenService.authenticate(email,token)) {
            expenseService.deleteExpense(email,token,expenseId);
            status = HttpStatus.OK;
            message = "Expense with id "+expenseId+" is removed successfully !!";
        }
        else {
            message = "Invalid User !!";
            status = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<String>(message,status);
    }


    @PostMapping("/product/email/{email}/token/{token}")
    public ResponseEntity<String> buyProduct(@PathVariable String email,@PathVariable String token ,@RequestBody Product product){
        HttpStatus status;
        String message;


        if(authTokenService.authenticate(email,token)){
            User user = authTokenService.findUserByToken(token);
            product.setUser(user);
            productService.buyProduct(product);
//            user.setExpense(p);
            message = "Your bill : "+product.getPrice();
            status = HttpStatus.OK;
        }
        else {
            message = "Invalid User !!";
            status = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<String>(message , status);
    }

    @GetMapping("/product/email/{email}/token/{token}")
    public ResponseEntity<Iterable<Product>> getAllProducts(@PathVariable String email,@PathVariable String token){
        HttpStatus status;
        String msg = "";
        Iterable<Product> myProducts = new ArrayList<>();
        if(authTokenService.authenticate(email,token))
        {
            User user =  authTokenService.findUserByToken(token);
            myProducts = productService.getAllProductsByUserId(Long.valueOf(user.getUserId()));
            status = HttpStatus.OK;
        }
        else
        {
            msg = "Invalid user";
            status = HttpStatus.FORBIDDEN;
        }

        return new ResponseEntity<>(myProducts, status);
    }

    @GetMapping("/product/email/{email}/token/{token}/purchaseDate/{purchaseDate}")
    public ResponseEntity<Iterable<Product>> getProductsByDate(@PathVariable String email,@PathVariable String token,@PathVariable String purchaseDate ){
        HttpStatus status;
        String msg = "";
        Iterable<Product> myProducts = new ArrayList<>();
        if(authTokenService.authenticate(email,token))
        {   LocalDateTime time = LocalDateTime.now();

            System.out.println(time);
            User user =  authTokenService.findUserByToken(token);
            myProducts = productService.getAllProductsByUserIdAndDate(Long.valueOf(user.getUserId()),purchaseDate);
            status = HttpStatus.OK;
        }
        else
        {
            msg = "Invalid user";
            status = HttpStatus.FORBIDDEN;
        }

        return new ResponseEntity<>(myProducts, status);
    }












}
