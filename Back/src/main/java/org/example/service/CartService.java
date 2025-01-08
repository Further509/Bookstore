package org.example.service;

import org.example.model.Cart;
import org.example.model.CartItem;
import org.example.model.Customer;
import org.example.repository.CartRepository;
import org.example.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Cart getCartByCustomerId(Long customerId) {
        var cart = cartRepository.findByCustomerId(customerId)
                .orElseGet(() -> createCartForCustomer(customerId));
        if (cart == null) {
            throw new RuntimeException("Cart not found for customer ID: " + customerId);
        }
        return cart;
    }


    private Cart createCartForCustomer(Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()) {
            throw new RuntimeException("Customer not found with id: " + customerId);
        }

        Customer customer = customerOptional.get();
        Cart cart = new Cart();
        cart.setCustomer(customer);
        cart.setItems(new ArrayList<>());
        return cartRepository.save(cart);
    }

    public Cart addBookToCart(Long customerId, Long bookId, Integer quantity) {
        Cart cart = getCartByCustomerId(customerId);
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getBookId().equals(bookId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem(bookId, quantity);
            cart.addItem(newItem);
        }

        return cartRepository.save(cart);
    }

    public Cart reduceBookQuantity(Long customerId, Long bookId, Integer quantity) {
        Cart cart = getCartByCustomerId(customerId);
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getBookId().equals(bookId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            int newQuantity = item.getQuantity() - quantity;
            if (newQuantity <= 0) {
                cart.getItems().remove(item);
            } else {
                item.setQuantity(newQuantity);
            }
        } else {
            throw new RuntimeException("Book not found in cart");
        }

        return cartRepository.save(cart);
    }

    public Cart deleteBookFromCart(Long customerId, Long bookId) {
        Cart cart = getCartByCustomerId(customerId);
        cart.getItems().removeIf(item -> item.getBookId().equals(bookId));
        return cartRepository.save(cart);
    }

    public void clearCart(Long customerId) {
        Cart cart = getCartByCustomerId(customerId);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
