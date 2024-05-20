package com.coco.repository;

import com.coco.entity.PaintingEntity;
import com.coco.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Set;

public interface PaintingRepository extends JpaRepository<PaintingEntity,Long>, JpaSpecificationExecutor<PaintingEntity> {

    Integer countByIdIn(List<Long> ids);

    Integer deleteByIdIn(List<Long> ids);

    Set<PaintingEntity> findByTopicsIn(Set<TopicEntity> topicEntities);

}
