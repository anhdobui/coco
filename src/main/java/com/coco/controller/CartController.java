package com.coco.controller;

import com.coco.dto.CartResDTO;
import com.coco.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/cart")
public class CartController {

    @Autowired
    private ICartService cartService;



    @GetMapping
    public CartResDTO getCartByAccount(@RequestParam(value = "accountId",required = true) Long accountId){
        return cartService.getCartByAccount(accountId);
    }



}
