package com.coco.service.impl;

import com.coco.dto.OrderReqDTO;
import com.coco.dto.OrdersDTO;
import com.coco.entity.CartEntity;
import com.coco.entity.OrdersEntity;
import com.coco.exception.CustomRuntimeException;
import com.coco.mapper.OrdersMapper;
import com.coco.repository.CartRepository;
import com.coco.repository.OrdersRepository;
import com.coco.service.IOrdersService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersService implements IOrdersService {


    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrdersMapper ordersMapper;


    @Override
    @Transactional
    public OrdersDTO orderPaintings(OrderReqDTO ordReq) {
        if(ordersRepository.existsByCart_Id(ordReq.getCartId()))
            throw new CustomRuntimeException("Đơn hàng đã cũ");
        if(checkCartOfAcc(ordReq)){
            OrdersEntity ordersEntity = new OrdersEntity();
            CartEntity cart = cartRepository.findById(ordReq.getCartId()).orElseThrow(() -> new CustomRuntimeException("Giỏ hàng không tồn tại"));
            ordersEntity.setCart(cart);
            ordersEntity = ordersRepository.save(ordersEntity);
            return ordersMapper.toDTO(ordersEntity);
        }
        throw new CustomRuntimeException("Lỗi giỏ hàng");
    }

    private boolean checkCartOfAcc(OrderReqDTO ordReq){
        CartEntity cart = cartService.getCartEntityByAccId(ordReq.getAccountId());
        if(cart != null){
            if (cart.getId() == ordReq.getCartId())
                return true;

        }

        return false;
    }
}
