package com.coco.dto;

import lombok.Data;

@Data
public class DetailReceivedLogDTO {
    private Long id;
    private Integer qty;
    private Double unitPrice;
    private Long paintingId;
    private String paintingCode;
    private Long receivedLogId;
}
