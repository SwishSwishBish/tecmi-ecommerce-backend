package com.sena.tecmiecommercebackend.dto.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AddToCardDto {
    private Integer id;
    private @NotNull Long productId;
    private @NotNull Integer quantity;
}
