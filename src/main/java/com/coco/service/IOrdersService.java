package com.coco.service;

import com.coco.dto.OrderFilterDTO;
import com.coco.dto.OrderReqDTO;
import com.coco.dto.OrdersDTO;
import com.coco.entity.OrdersEntity;
import com.coco.exception.DataNotFoundException;

import java.util.List;

public interface IOrdersService {

    OrdersDTO orderPaintings(OrderReqDTO ordReq);
<<<<<<< HEAD
    OrdersEntity updateOrder(Long orderId, OrdersDTO ordersDTO) throws DataNotFoundException;
=======

    List<OrdersDTO> getByCondition(OrderFilterDTO orderFilterDTO);

    OrdersDTO updateStatus(Integer status, Long id);
>>>>>>> main
}
