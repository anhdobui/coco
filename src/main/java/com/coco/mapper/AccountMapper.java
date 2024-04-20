package com.coco.mapper;

import com.coco.dto.AccountDTO;
import com.coco.dto.AccountResDTO;
import com.coco.dto.CustomerDTO;
import com.coco.entity.AccountEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;

    public AccountEntity toEntity(AccountDTO dto){
        AccountEntity result = modelMapper.map(dto,AccountEntity.class);
        result.setPassword(passwordEncoder.encode(dto.getPassword()));
        return result;
    }
    public AccountResDTO toDTO(AccountEntity entity){
        AccountResDTO result = modelMapper.map(entity,AccountResDTO.class);
        return result;
    }

    public CustomerDTO toCustDTO(AccountEntity entity){
        CustomerDTO result = modelMapper.map(entity,CustomerDTO.class);
        return result;
    }
}
