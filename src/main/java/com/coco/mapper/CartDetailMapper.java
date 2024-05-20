package com.coco.mapper;

import com.coco.dto.CartDetailResDTO;
import com.coco.entity.CartDetailEntity;
import com.coco.entity.CartEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class CartDetailMapper {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PaintingMapper paintingMapper;

    public CartDetailResDTO toResDTO(CartDetailEntity cartDetailEntity){
        CartDetailResDTO result = modelMapper.map(cartDetailEntity,CartDetailResDTO.class);
        BigDecimal bdQty = new BigDecimal(cartDetailEntity.getQty());
        BigDecimal bdPrice = new BigDecimal(cartDetailEntity.getPainting().getPrice());
        BigDecimal total = bdQty.multiply(bdPrice).setScale(2, RoundingMode.HALF_UP);
        result.setTotal(total);
        result.setPainting(paintingMapper.toResDTO(cartDetailEntity.getPainting()));
        return result;
    }
    public CartDetailResDTO toResDTO(CartDetailEntity cartDetailEntity,CartEntity cartEntity){
        CartDetailResDTO result = modelMapper.map(cartDetailEntity,CartDetailResDTO.class);
        result.setCartId(cartEntity.getId());
        BigDecimal bdQty = new BigDecimal(cartDetailEntity.getQty());
        BigDecimal bdPrice = new BigDecimal(cartDetailEntity.getPainting().getPrice());
        BigDecimal total = bdQty.multiply(bdPrice).setScale(2, RoundingMode.HALF_UP);
        result.setTotal(total);
        return result;
    }
}
