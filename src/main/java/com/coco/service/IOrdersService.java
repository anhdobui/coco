package com.coco.service;

import com.coco.dto.OrderReqDTO;
import com.coco.dto.OrdersDTO;
import com.coco.entity.OrdersEntity;
import com.coco.exception.DataNotFoundException;

public interface IOrdersService {

    OrdersDTO orderPaintings(OrderReqDTO ordReq);
    OrdersEntity updateOrder(Long orderId, OrdersDTO ordersDTO) throws DataNotFoundException;
}
