package com.coco.repository;

import com.coco.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrdersRepository extends JpaRepository<OrdersEntity,Long>, JpaSpecificationExecutor<OrdersEntity> {

    boolean existsByCart_Id(Long cartId);
}
