package com.coco.dto;

import lombok.Data;

@Data
public class OrderReqDTO {
    private Long cartId;
    private Long accountId;
    private String deliveryAddress;
    private Double shippingCost;
    private Integer paymentStatus;
}
