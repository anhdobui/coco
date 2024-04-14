package com.coco.dto;

import lombok.Data;

import java.util.List;

@Data
public class TopicResDTO {
    private Long id;

    private String title;

    private String description;

    private List<PaintingDTO> paintings;
}
