package com.coco.service.impl;

import com.coco.dto.AccountDTO;
import com.coco.dto.AccountFilterDTO;
import com.coco.dto.AccountLoginDTO;
import com.coco.dto.AccountResDTO;
import com.coco.entity.AccountEntity;
import com.coco.exception.CustomRuntimeException;
import com.coco.mapper.AccountMapper;
import com.coco.repository.AccountRepository;
import com.coco.service.IAccountService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService implements IAccountService {


    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AccountResDTO save(AccountDTO acc) {
        Boolean accountIsExist = accountRepository.existsByUsername(acc.getUsername());
        if(!accountIsExist){
            AccountEntity accountEntity = accountMapper.toEntity(acc);
            accountEntity = accountRepository.save(accountEntity);
            return accountMapper.toResDTO(accountEntity);
        }else{
            throw new CustomRuntimeException("Tài khoản đã tồn tại");
        }

    }

    @Override
    public AccountResDTO login(AccountLoginDTO acc) {
        AccountEntity accountEntity = accountRepository.findByUsername(acc.getUsername()).orElse(null);
        if(accountEntity != null){
            if(passwordEncoder.matches(acc.getPassword(), accountEntity.getPassword())){
                return accountMapper.toResDTO(accountEntity);
            }
        }
        throw new CustomRuntimeException("Tài khoản hoặc mật khẩu không chính xác");
    }

    @Override
    public List<AccountResDTO> getByCondition(AccountFilterDTO accountFilterDTO) {
        List<AccountEntity> accounts = accountRepository.findAll(filterByCondition(accountFilterDTO));
        return accounts.stream().map(accountMapper::toResDTO).collect(Collectors.toList());
    }

    private Specification<AccountEntity> filterByCondition(AccountFilterDTO filter) {
        return (Root<AccountEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (filter.getFullname() != null && !filter.getFullname().isEmpty()) {
                predicate = cb.and(predicate, cb.like(root.get("fullname"), "%" + filter.getFullname() + "%"));
            }
            if (filter.getUsername() != null && !filter.getUsername().isEmpty()) {
                predicate = cb.and(predicate, cb.like(root.get("username"), "%" + filter.getUsername() + "%"));
            }
            if (filter.getEmail() != null && !filter.getEmail().isEmpty()) {
                predicate = cb.and(predicate, cb.like(root.get("email"), "%" + filter.getEmail() + "%"));
            }
            if (filter.getPhone() != null && !filter.getPhone().isEmpty()) {
                predicate = cb.and(predicate, cb.like(root.get("phone"), "%" + filter.getPhone() + "%"));
            }
            if (filter.getCity() != null && !filter.getCity().isEmpty()) {
                predicate = cb.and(predicate, cb.like(root.get("city"), "%" + filter.getCity() + "%"));
            }
            if (filter.getDistrict() != null && !filter.getDistrict().isEmpty()) {
                predicate = cb.and(predicate, cb.like(root.get("district"), "%" + filter.getDistrict() + "%"));
            }
            if (filter.getWard() != null && !filter.getWard().isEmpty()) {
                predicate = cb.and(predicate, cb.like(root.get("ward"), "%" + filter.getWard() + "%"));
            }
            if (filter.getMinGrade() != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("grade"), filter.getMinGrade()));
            }
            if (filter.getMaxGrade() != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("grade"), filter.getMaxGrade()));
            }
            query.orderBy(cb.desc(root.get("grade")));
            return predicate;
        };
    }
}
