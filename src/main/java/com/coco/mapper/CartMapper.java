package com.coco.mapper;

import com.coco.dto.CartDetailResDTO;
import com.coco.dto.CartResDTO;
import com.coco.entity.CartEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartDetailMapper cartDetailMapper;

    public CartResDTO toResDTO(CartEntity entity){
        CartResDTO result = modelMapper.map(entity,CartResDTO.class);
        result.setAccountId(entity.getAcc().getId());
        BigDecimal total = calculationTotalCart(entity);
        result.setTotal(total);
        if(entity.getCartDetails() != null ){
            List<CartDetailResDTO> cartDetailResDTOs = entity.getCartDetails().stream().map(cartDetailMapper::toResDTO).collect(Collectors.toList());
            result.setDetails(cartDetailResDTOs);
        }
        return result;
    }

    public BigDecimal calculationTotalCart(CartEntity cartEntity){
        if(cartEntity.getCartDetails() != null ){
            BigDecimal total = cartEntity.getCartDetails().stream()
                    .map(cartDetail -> {
                        BigDecimal price = BigDecimal.valueOf(cartDetail.getPainting().getPrice());
                        BigDecimal qty = BigDecimal.valueOf(cartDetail.getQty());
                        return price.multiply(qty);
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            total = total.setScale(2, RoundingMode.HALF_UP);
            return total;
        }
        return new BigDecimal(0);
    }
}
