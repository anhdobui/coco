package com.coco.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OrderFilterDTO {
    private Long accountId;
    private String orderStatus;
    private String paymentStatus;
    private Date startOrderDate;
}
