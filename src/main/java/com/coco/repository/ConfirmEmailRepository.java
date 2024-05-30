package com.coco.repository;

import com.coco.entity.ConfirmEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmEmailRepository extends JpaRepository<ConfirmEmail, Long> {
    ConfirmEmail findConfirmEmailByCode(String code);
}
