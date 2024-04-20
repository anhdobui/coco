package com.coco.mapper;

import com.coco.dto.CartDetailResDTO;
import com.coco.dto.CustomerDTO;
import com.coco.dto.OrdersDTO;
import com.coco.entity.OrdersEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrdersMapper {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartDetailMapper cartDetailMapper;

    @Autowired
    private AccountMapper accountMapper;

    public OrdersDTO toDTO(OrdersEntity entity){
        OrdersDTO result = modelMapper.map(entity,OrdersDTO.class);
        if(entity.getCart().getCartDetails() != null ){
            Double total = entity.getCart().getCartDetails().stream()
                    .mapToDouble(cartDetail -> cartDetail.getPainting().getPrice() * cartDetail.getQty())
                    .sum();
            List<CartDetailResDTO> cartDetailResDTOs = entity.getCart().getCartDetails().stream().map(cartDetailMapper::toResDTO).collect(Collectors.toList());
            result.setDetails(cartDetailResDTOs);
            result.setTotal(total);
        }
        if(entity.getCart().getAcc() != null){
            CustomerDTO cust = accountMapper.toCustDTO(entity.getCart().getAcc());
            result.setCust(cust);
        }
        return result;
    }
}
