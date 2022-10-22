package com.example.homeworkseven2.services;
import com.example.homeworkseven2.models.Cart;
import java.util.List;

public interface CartService {
    void addCartByPersonUsername(String username);

    void removeCartById(Long id);

    Cart getCartById(Long id);

    List<Cart> getAllCarts();

    List<Cart> getAllPersonCarts(String username);

    void addProductByProductId(Long cartId, Long productId);

    void removeProductByProductId(Long cartId, Long productId);

    void removeAllProductsById(Long id);

}

