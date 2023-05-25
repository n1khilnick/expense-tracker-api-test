package com.geekster.expensetracker.Services;


import com.geekster.expensetracker.dto.SignInInput;
import com.geekster.expensetracker.dto.SignInOutput;
import com.geekster.expensetracker.dto.SignUpOutput;
import com.geekster.expensetracker.models.AuthToken;
import com.geekster.expensetracker.models.User;
import com.geekster.expensetracker.repositories.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {
    @Autowired
    IUserDao userDao;

    @Autowired
    AuthTokenService tokenService;


    public SignUpOutput signUp(User signUpDto) {

        //checks if user exists or not based on email
        User user = userDao.findFirstByUserEmail(signUpDto.getUserEmail());

        if(user != null){
            throw new IllegalStateException("User already exists !! Sign-In instead");
        }

        //encryption
        String encryptedPassword = null;
        try {
            encryptedPassword = encryptPassword(signUpDto.getUserPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //save the user

        user = new User(signUpDto.getUserFullName(),signUpDto.getUserEmail(),signUpDto.getUserUserName(),encryptedPassword);

        userDao.save(user);

        return new SignUpOutput("Signup Successfully !!","User created Successfully !!");
    }

    private String encryptPassword(String userPassword) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(userPassword.getBytes());
        byte[] digested = md5.digest();

        String hash = DatatypeConverter.printHexBinary(digested);
        System.out.println(hash);
        return hash;
    }


    public SignInOutput signIn(SignInInput signInDto) {

        //check user based on email
        User user = userDao.findFirstByUserEmail(signInDto.getUserEmail());

        if(user == null){
            throw new IllegalStateException("User doesn't exists !! check details or Sign-up first");
        }

        //encrypt the password

        String encryptedPassword = null;

        try {
            encryptedPassword = encryptPassword(signInDto.getUserPassword());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        //match it with database encrypted password

        boolean isPasswordValid  = encryptedPassword.equals(user.getUserPassword());

        if(!isPasswordValid){
            throw new IllegalStateException("User's details are invalid !! check details or Sign-up first");
        }

        //token creation and saving : session

        AuthToken token = new AuthToken(user);

        tokenService.saveToken(token);

        //set up output response

        return new SignInOutput("Authentication Successfully !!",token.getToken());
    }
}
