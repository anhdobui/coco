package com.coco.controller;

import com.coco.dto.OrderReqDTO;
import com.coco.dto.OrdersDTO;
import com.coco.entity.OrdersEntity;
import com.coco.exception.DataNotFoundException;
import com.coco.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/orders")
public class OrdersController {

    @Autowired
    private IOrdersService ordersService;

    @PostMapping
    public OrdersDTO postOrders(@RequestBody OrderReqDTO ordReq){
        return ordersService.orderPaintings(ordReq);
    }
    @PutMapping
    public OrdersEntity updateOrder(@RequestParam Long id, @RequestBody OrdersDTO ordersDTO) throws DataNotFoundException {
        return ordersService.updateOrder(id, ordersDTO);
    }
}
