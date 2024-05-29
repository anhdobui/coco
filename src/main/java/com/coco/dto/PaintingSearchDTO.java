package com.coco.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaintingSearchDTO {
    private Long id;
    private String code;
    private String name;
    private String artist;
    private Double length;
    private Double width;
    private Double thickness;
    private Double priceFrom;
    private Double priceTo;
    private List<Long> topicIds;
}
