package com.coco.service;

import com.coco.dto.PaintingDTO;

import java.util.List;

public interface IPaintingService {
    PaintingDTO save(PaintingDTO paintingDTO) ;

    Integer delete(List<Long> ids);
}
