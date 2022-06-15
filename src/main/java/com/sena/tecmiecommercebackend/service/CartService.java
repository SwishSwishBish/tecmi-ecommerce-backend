package com.sena.tecmiecommercebackend.service;

import com.sena.tecmiecommercebackend.dto.cart.AddToCardDto;
import com.sena.tecmiecommercebackend.dto.cart.CartDto;
import com.sena.tecmiecommercebackend.dto.cart.CartItemDto;
import com.sena.tecmiecommercebackend.exceptions.CartItemNotExistException;
import com.sena.tecmiecommercebackend.exceptions.CustomException;
import com.sena.tecmiecommercebackend.repository.ICartRepository;
import com.sena.tecmiecommercebackend.repository.entity.Cart;
import com.sena.tecmiecommercebackend.repository.entity.Product;
import com.sena.tecmiecommercebackend.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService {

    @Autowired
    ICartRepository cartRepository;

    public void addToCart(AddToCardDto addToCartDto, Product product, User user) {
        Cart cart = new Cart(product, addToCartDto.getQuantity(), user);
        cartRepository.save(cart);
    }

    public CartDto listCartItems(User user) {
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<CartItemDto> cartItems = new ArrayList<>();
        for (Cart cart:cartList){
            CartItemDto cartItemDto = getDtoFromCart(cart);
            cartItems.add(cartItemDto);
        }
        double totalCost = 0;
        for (CartItemDto cartItemDto :cartItems){
            totalCost += (cartItemDto.getProduct().getPrice()* cartItemDto.getQuantity());
        }
        return new CartDto(cartItems,totalCost);
    }

    private CartItemDto getDtoFromCart(Cart cart) {
        return new CartItemDto(cart);
    }

    public void deleteCartItem(Integer cartItemId, User user) throws CartItemNotExistException {
        Optional<Cart> optionalCart = cartRepository.findById(cartItemId);
        if(optionalCart.isEmpty()){
            throw new CustomException("Cart item id is invalid => " + cartItemId);
        }
        Cart cart = optionalCart.get();
        if(cart.getUser()!= user){
            throw new CustomException("Cart item does not belong to user: " + cartItemId);
        }
        cartRepository.delete(cart);
    }

    public void updateCartItem(AddToCardDto cartDto, User user) {
        Cart cart = cartRepository.getOne(cartDto.getId());
        if(cart.getUser()!= user){
            throw new CustomException("Cart item does not belong to user: " + cartDto);
        }
        cart.setQuantity(cartDto.getQuantity());
        cart.setCreatedDate(new Date());
        cartRepository.save(cart);
    }

    public void deleteUserCartItems(User user) {
        cartRepository.deleteByUser(user);
    }
}
