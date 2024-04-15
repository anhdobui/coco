package com.coco.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReceivedLogDTO {
    private Long id;
    private String code;
    private String note;
    private List<DetailReceivedLogDTO> detail;
}
