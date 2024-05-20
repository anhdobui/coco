package com.coco.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaintingResDTO {
    private Long id;
    private String code;
    private String name;
    private String artist;
    private Double length;
    private Double width;
    private Double thickness;
    private Integer inventory;
    private Double price;
    private String thumbnailUrl;
    private List<String> albumUrl;
    private List<TopicDTO> topics;
}
