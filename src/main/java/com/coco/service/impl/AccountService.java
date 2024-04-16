package com.coco.service.impl;

import com.coco.dto.AccountDTO;
import com.coco.dto.AccountLoginDTO;
import com.coco.dto.AccountResDTO;
import com.coco.entity.AccountEntity;
import com.coco.exception.CustomRuntimeException;
import com.coco.mapper.AccountMapper;
import com.coco.repository.AccountRepository;
import com.coco.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        AccountEntity accountEntity = accountMapper.toEntity(acc);
        accountEntity = accountRepository.save(accountEntity);
        return accountMapper.toDTO(accountEntity);
    }

    @Override
    public AccountResDTO login(AccountLoginDTO acc) {
        AccountEntity accountEntity = accountRepository.findByUsername(acc.getUsername()).orElse(null);
        if(accountEntity != null){
            if(passwordEncoder.matches(acc.getPassword(), accountEntity.getPassword())){
                return accountMapper.toDTO(accountEntity);
            }
        }
        throw new CustomRuntimeException("Tài khoản hoặc mật khẩu không chính xác");
    }
}
