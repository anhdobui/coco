package com.coco.controller;

import com.coco.dto.OrderReqDTO;
import com.coco.dto.OrdersDTO;
import com.coco.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/orders")
public class OrdersController {

    @Autowired
    private IOrdersService ordersService;

    @PostMapping
    public OrdersDTO postOrders(@RequestBody OrderReqDTO ordReq){
        return ordersService.orderPaintings(ordReq);
    }
}
