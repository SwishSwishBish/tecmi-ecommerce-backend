package com.sena.tecmiecommercebackend.dto.order;

import com.sena.tecmiecommercebackend.repository.entity.Order;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderDto {
    private Integer id;
    private @NotNull Long userId;

    public OrderDto(Order order) {
        this.setId(order.getId());
    }
}
