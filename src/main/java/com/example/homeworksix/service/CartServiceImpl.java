package com.example.homeworksix.service;

import com.example.homeworksix.dto.CartDto;
import com.example.homeworksix.model.Cart;
import com.example.homeworksix.model.Product;
import com.example.homeworksix.repository.CartRepository;
import com.example.homeworksix.utils.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.example.homeworksix.converter.CartConverter.getCartDtoFromCart;
import static com.example.homeworksix.converter.PersonConverter.getPersonFromPersonDto;
import static com.example.homeworksix.converter.ProductConverter.getProductFromProductDto;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final ProductService productService;

    private final PersonService personService;

    public CartServiceImpl(CartRepository cartRepository, ProductService productService, PersonService personService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.personService = personService;
    }

    @Override
    public CartDto createCartByPersonId(Long id) throws NotFoundException {
        Cart cart = new Cart(getPersonFromPersonDto(personService.getPersonById(id)));
        personService.getPersonById(id).getCarts().add(cart);
        return getCartDtoFromCart(cartRepository.save(cart));
    }

    @Override
    public CartDto addProductByProductIdAndCartId(CartDto cartDto) throws NotFoundException {
        Cart cart = cartRepository.findById(cartDto.getCartId()).get();
        Product product = getProductFromProductDto(productService.getProductById(cartDto.getProductId()));
        if (cartRepository.findById(cartDto.getCartId()).isPresent()) {
            cart.getProducts().add(product);
            cart.setSum(cart.getSum().add(BigDecimal.valueOf(productService.getProductById(cartDto.getProductId()).getPrice())));
            cartRepository.save(cart);
            return getCartDtoFromCart(cart);
        } else {
            throw new NotFoundException("Cart with ID #" + cartDto.getCartId() + " is not found");
        }
    }

    @Override
    public CartDto removeProductByProductIdAndCartId(CartDto cartDto) throws NotFoundException {
        Cart cart = cartRepository.findById(cartDto.getCartId()).get();
        Product product = getProductFromProductDto(productService.getProductById(cartDto.getProductId()));
        if (cartRepository.findById(cartDto.getCartId()).isPresent()) {
            cart.getProducts().remove(product);
            if (cart.getSum().compareTo(new BigDecimal("0.0")) != 0) {
                cart.setSum(cart.getSum().subtract(BigDecimal.valueOf(productService.getProductById(cartDto.getProductId()).getPrice())));
            } else {
                cart.setSum(BigDecimal.valueOf(0.0));
            }
            cartRepository.save(cart);
            return getCartDtoFromCart(cart);
        } else {
            throw new NotFoundException("Cart with ID #" + cartDto.getCartId() + " is not found");
        }
    }

    @Override
    public void removeAllProductsFromCartById(Long cartId) throws NotFoundException {
        if (cartRepository.findById(cartId).isPresent()) {
            Cart cart = cartRepository.findById(cartId).get();
            cart.getProducts().clear();
            cart.setSum(new BigDecimal("0.00"));
            cartRepository.save(cart);
        } else {
            throw new NotFoundException("Cart with ID #" + cartId + " is empty");
        }
    }

    @Override
    public CartDto getCartById(Long cartId) throws NotFoundException {
        if (cartRepository.findById(cartId).isPresent()) {
            return getCartDtoFromCart(cartRepository.findById(cartId).get());
        } else {
            throw new NotFoundException("Cart with ID #" + cartId + " is not found");
        }
    }

    @Override
    public void removeCartById(Long cartId) throws NotFoundException {
        if (cartRepository.findById(cartId).isPresent()) {
            Cart cart = cartRepository.findById(cartId).get();
            personService.getPersonById(cart.getPerson().getId()).getCarts().remove(cart);
            cartRepository.deleteById(cartId);
        } else {
            throw new NotFoundException("Cart with ID #" + cartId + " is not found");
        }
    }

    @Override
    public List<CartDto> getAllCarts() {
        List<CartDto> cartDtoList = new ArrayList<>();
        cartRepository.findAll().forEach(cart -> cartDtoList.add(getCartDtoFromCart(cart)));
        return cartDtoList;
    }

}