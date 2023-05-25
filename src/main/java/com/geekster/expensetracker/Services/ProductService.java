package com.geekster.expensetracker.Services;

import com.geekster.expensetracker.models.Product;
import com.geekster.expensetracker.repositories.IProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    IProductDao productDao;

    public void buyProduct(Product product) {
        productDao.save(product);
    }


    public Iterable<Product> getAllProductsByUserId(Long userId) {
        return productDao.findAllProductsByUserId(userId);
    }

    public Iterable<Product> getAllProductsByUserIdAndDate(Long userId, String purchaseDate) {
        return productDao.findAllProductsByUserIdAndDate(userId,purchaseDate);
    }
}
