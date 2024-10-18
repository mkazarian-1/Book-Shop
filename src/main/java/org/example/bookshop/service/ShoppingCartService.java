package org.example.bookshop.service;

import org.example.bookshop.model.ShoppingCart;
import org.example.bookshop.model.User;

public interface ShoppingCartService {
    void createShoppingCart(User user);

    ShoppingCart getShoppingCartByCurrentUser();
}
