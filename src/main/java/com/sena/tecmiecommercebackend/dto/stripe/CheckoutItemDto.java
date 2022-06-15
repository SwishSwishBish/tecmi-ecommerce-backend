package com.sena.tecmiecommercebackend.dto.stripe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutItemDto {
    private String productName;
    private int  quantity;
    private double price;
    private Long productId;
    private Long userId;
}
