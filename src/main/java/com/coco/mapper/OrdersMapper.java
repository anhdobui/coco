package com.coco.mapper;

import com.coco.dto.CartDetailResDTO;
import com.coco.dto.CustomerDTO;
import com.coco.dto.OrdersDTO;
import com.coco.entity.OrdersEntity;
import com.coco.enumDefine.StatusOrderEnum;
import com.coco.enumDefine.StatusPaymentEnum;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
        StatusOrderEnum statusEnum = StatusOrderEnum.fromValue(entity.getStatus());
        result.setStatus(statusEnum.toString());
        StatusPaymentEnum statusPaymentEnum = StatusPaymentEnum.fromValue(entity.getPaymentStatus());
        result.setPaymentStatus(statusPaymentEnum.name());
        if(entity.getCart().getCartDetails() != null ){
            BigDecimal total = entity.getCart().getCartDetails().stream()
                    .map(cartDetailMapper::calculationTotalCartDetail)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            List<CartDetailResDTO> cartDetailResDTOs = entity.getCart().getCartDetails().stream().map(cartDetailMapper::toResDTO).collect(Collectors.toList());
            result.setDetails(cartDetailResDTOs);
            if(entity.getShippingCost() != null){
                BigDecimal shippingCost = new BigDecimal(entity.getShippingCost());
                total = total.add(shippingCost);
            }
            result.setTotal(total);
        }
        if(entity.getCart().getAcc() != null){
            CustomerDTO cust = accountMapper.toCustDTO(entity.getCart().getAcc());
            result.setCust(cust);
        }
        return result;
    }
}
