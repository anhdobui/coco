package com.coco.service;

import com.coco.dto.OrderFilterDTO;
import com.coco.dto.OrderReqDTO;
import com.coco.dto.OrdersDTO;

import java.util.List;

public interface IOrdersService {

    OrdersDTO orderPaintings(OrderReqDTO ordReq);


    List<OrdersDTO> getByCondition(OrderFilterDTO orderFilterDTO);

    OrdersDTO updateStatus(Integer status, Long id);

    void updateVnpayUrl(Long orderId,String url);
}
