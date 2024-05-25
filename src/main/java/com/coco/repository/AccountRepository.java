package com.coco.repository;

import com.coco.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity,Long>, JpaSpecificationExecutor<AccountEntity> {

    Optional<AccountEntity> findByUsername(String username);
    boolean existsByUsername(String username);
}
