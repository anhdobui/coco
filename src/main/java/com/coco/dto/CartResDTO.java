package com.coco.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartResDTO {
    private Long id;
    private Integer status;
    private AccountDTO account;
    private List<CartDetailResDTO> details;
    private Double total;
}
