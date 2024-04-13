package com.coco.repository;

import com.coco.entity.PaintingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaintingRepository extends JpaRepository<PaintingEntity,Long> {

    Integer countByIdIn(List<Long> ids);

    Integer deleteByIdIn(List<Long> ids);

}
