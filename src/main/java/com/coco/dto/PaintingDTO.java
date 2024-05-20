package com.coco.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaintingDTO {

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
    private List<Long> topicIds;

}
