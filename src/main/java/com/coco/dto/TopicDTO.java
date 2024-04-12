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
public class TopicDTO {
    private Long id;

    private String title;

    private String description;

    private List<Long> paintingIds;
}
