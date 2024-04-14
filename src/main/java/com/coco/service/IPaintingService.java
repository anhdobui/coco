package com.coco.service;

import com.coco.dto.PaintingDTO;
import com.coco.dto.PaintingResDTO;
import com.coco.dto.PaintingSearchDTO;
import com.coco.entity.PaintingEntity;
import com.coco.entity.TopicEntity;

import java.util.List;
import java.util.Set;

public interface IPaintingService {
    PaintingDTO save(PaintingDTO paintingDTO) ;

    Integer delete(List<Long> ids);

    PaintingEntity attachTopic(PaintingEntity paintingEntity, Set<TopicEntity> topics);

    PaintingResDTO findById(Long id);

    List<PaintingResDTO> findByCondition(PaintingSearchDTO paintingSearch);
}
