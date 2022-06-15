package com.sena.tecmiecommercebackend.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CartDto {
    private List<CartItemDto> cartItems;
    private double totalCost;
}
