package com.coco.mapper;

import com.coco.dto.CartDetailResDTO;
import com.coco.entity.CartDetailEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartDetailMapper {
    @Autowired
    private ModelMapper modelMapper;

    public CartDetailResDTO toResDTO(CartDetailEntity entity){
        CartDetailResDTO result = modelMapper.map(entity,CartDetailResDTO.class);
        return result;
    }
}
