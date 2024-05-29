package com.coco.controller;

import com.coco.dto.OrderFilterDTO;
import com.coco.dto.OrderReqDTO;
import com.coco.dto.OrderStatusDTO;
import com.coco.dto.OrdersDTO;
import com.coco.entity.OrdersEntity;
import com.coco.exception.DataNotFoundException;
import com.coco.enumDefine.StatusOrderEnum;
import com.coco.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public List<OrdersDTO> getByCondition(@ModelAttribute OrderFilterDTO orderFilterDTO){
        return ordersService.getByCondition(orderFilterDTO);
    }

    @PutMapping("{id}")
    public OrdersDTO updateStatus(@RequestBody OrderStatusDTO orderStatusDTO, @PathVariable(value = "id") Long id){
        StatusOrderEnum statusEnum = StatusOrderEnum.fromString(orderStatusDTO.getStatus());
        return ordersService.updateStatus(statusEnum.getValue(), id);
    }
}
