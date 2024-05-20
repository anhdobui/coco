package com.coco.dto;

import lombok.Data;

@Data
public class CartDetailReqDTO {
    private Long id;
    private Integer qty;
    private Long paintingId;
    private Long accountId;
}
