package com.coco.repository;

import com.coco.entity.CartDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartDetailRepository extends JpaRepository<CartDetailEntity,Long> {
    Long countByCartAccIdAndCartStatus(Long accountId,Integer status);
}
