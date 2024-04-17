package com.coco.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartReqDTO {
    private Long accountId;
    private List<CartDetailReqDTO> details;
}
