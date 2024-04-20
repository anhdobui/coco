package com.coco.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrdersDTO {
    private String code;
    private Integer status;
    private Date orderDate;
    private Date deliveryDate;
    private Date cancellationDate;
    private Date finishedDate;
    private CustomerDTO cust;
    private List<CartDetailResDTO> details;
    private Double total;
}
