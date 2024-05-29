package com.coco.dto;

import lombok.Data;

@Data
public class CartPaintingDTO {
    private Long accountId;
    private Long paintingId;
    private Integer qty;
}
