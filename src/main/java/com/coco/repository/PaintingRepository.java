package com.coco.repository;

import com.coco.entity.PaintingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaintingRepository extends JpaRepository<PaintingEntity,Long> {

}
