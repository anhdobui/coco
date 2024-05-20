package com.coco.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartResDTO {
    private Long id;
    private Integer status;
    private Long accountId;
    private List<CartDetailResDTO> details;
    private BigDecimal total;
}
