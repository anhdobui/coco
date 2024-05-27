package com.coco.repository;

import com.coco.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity,Long> {
    Optional<CartEntity> findFirstByStatusAndAccIdOrderByCreatedDateDesc(Integer status, Long accId);
    @Query("SELECT CASE WHEN COUNT(cd) > 0 THEN TRUE ELSE FALSE END FROM CartEntity c LEFT JOIN c.cartDetails cd WHERE c.id = :cartId")
    boolean hasCartDetails(@Param("cartId") Long cartId);
    Optional<CartEntity> findByIdAndStatusEquals(Long accId ,Integer status);

    Optional<CartEntity> findByAcc_Id(Long accId);

}
