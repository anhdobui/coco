package com.coco.repository;

import com.coco.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<OrdersEntity,Long> {

    boolean existsByCart_Id(Long cartId);
}
