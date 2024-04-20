package com.coco.service;

import com.coco.dto.OrderReqDTO;
import com.coco.dto.OrdersDTO;

public interface IOrdersService {

    OrdersDTO orderPaintings(OrderReqDTO ordReq);
}
