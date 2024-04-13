package com.coco.repository;

import com.coco.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<TopicEntity,Long> {
    Integer countByIdIn(List<Long> ids);

    Integer deleteByIdIn(List<Long> ids);
}
