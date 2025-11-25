package com.ssg.gallery.order.dto;

import com.ssg.gallery.order.entity.Order;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class OrderRequest {

    private String name;
    private String address;
    private String payment;
    private String cardNumber;
    private Long amount;
    private List<Integer> itemIds;

    public Order toEntity(Integer memberId) {
        return new Order(memberId, name, address, payment, cardNumber, amount);
    }
}