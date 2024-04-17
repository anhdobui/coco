package com.coco.controller;

import com.coco.dto.CartPaintingDTO;
import com.coco.dto.CartResDTO;
import com.coco.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/cart")
public class CartController {

    @Autowired
    private ICartService cartService;

    @PostMapping("/painting")
    public Integer addPaintingToCart (@RequestBody CartPaintingDTO cartPaintingDTO) {
        return cartService.addPaintingToCart(cartPaintingDTO);
    }

    @GetMapping
    public CartResDTO getCartByAccount(@RequestParam(value = "accountId",required = true) Long accountId){
        return cartService.getCartByAccount(accountId);
    }

}
