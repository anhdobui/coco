package com.coco.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartDetailResDTO {
    private Long id;
    private Integer qty;
    private PaintingResDTO painting;
    private Long cartId;
    private BigDecimal total;

}
