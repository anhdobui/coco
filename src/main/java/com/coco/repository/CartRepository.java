package com.coco.repository;

import com.coco.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity,Long> {
    Optional<CartEntity> findFirstByStatusAndAccIdOrderByCreatedDateDesc(Integer status, Long accId);

    Integer countCartDetailsByAccId(Long acc_id);
}