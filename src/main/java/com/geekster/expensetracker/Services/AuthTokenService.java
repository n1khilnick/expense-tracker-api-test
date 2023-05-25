package com.geekster.expensetracker.Services;

import com.geekster.expensetracker.models.AuthToken;
import com.geekster.expensetracker.models.User;
import com.geekster.expensetracker.repositories.IAuthTokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthTokenService {
    @Autowired
    IAuthTokenDao tokenDao;

    public void saveToken(AuthToken token){
        tokenDao.save(token);
    }

    public AuthToken getToken(User user) {
         return tokenDao.findByUser(user);
    }

    public boolean authenticate(String userEmail, String token) {
        if(token==null && userEmail==null){
            return false;
        }

        AuthToken authToken = tokenDao.findFirstByToken(token);

        if(authToken==null){
            return false;
        }

        String expectedEmail = authToken.getUser().getUserEmail();


        return expectedEmail.equals(userEmail);
    }

    public void deleteToken(String token) {
        AuthToken token1 = tokenDao.findFirstByToken(token);

        tokenDao.deleteById(token1.getTokenId());
    }

    public User findUserByToken(String token) {
        return tokenDao.findFirstByToken(token).getUser();
    }
}
