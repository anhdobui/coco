package com.coco.dto;

import lombok.Data;

@Data
public class CartDetailResDTO {
    private Long id;
    private Integer qty;
    private PaintingDTO painting;

}
