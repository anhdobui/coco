package com.coco.dto;

import lombok.Builder;

@Builder
public class PaymentDTO {
    public String code;
    public String message;
    public String paymentUrl;
}
