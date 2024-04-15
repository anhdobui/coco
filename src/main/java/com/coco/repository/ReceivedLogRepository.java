package com.coco.repository;

import com.coco.entity.ReceivedLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceivedLogRepository extends JpaRepository<ReceivedLogEntity,Long> {
}
