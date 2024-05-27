package com.coco.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrdersDTO {
    private Long id;
    private String code;
    private String status;
    private Date orderDate;
    private Date deliveryDate;
    private Date cancellationDate;
    private Date finishedDate;
    private CustomerDTO cust;
    private List<CartDetailResDTO> details;
    private String deliveryAddress;
    private Double shippingCost;
    private String paymentStatus;
    private BigDecimal total;
}
