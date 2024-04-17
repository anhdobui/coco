package com.coco.mapper;

import com.coco.dto.CartDetailResDTO;
import com.coco.dto.CartResDTO;
import com.coco.entity.CartEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        if(entity.getCartDetails() != null ){
            Double total = entity.getCartDetails().stream()
                    .mapToDouble(cartDetail -> cartDetail.getPainting().getPrice() * cartDetail.getQty())
                    .sum();
            List<CartDetailResDTO> cartDetailResDTOs = entity.getCartDetails().stream().map(cartDetailMapper::toResDTO).collect(Collectors.toList());
            result.setDetails(cartDetailResDTOs);
            result.setTotal(total);
        }
        return result;
    }
}
