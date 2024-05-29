package com.coco.controller;

import com.coco.dto.CartDetailReqDTO;
import com.coco.dto.CartDetailResDTO;
import com.coco.service.ICartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/cart-detail")
public class CartDetailController {

    @Autowired
    private ICartDetailService cartDetailService;
    @PostMapping()
    public CartDetailResDTO addCartDetail (@RequestBody CartDetailReqDTO cartDetailReqDTO) {
        return cartDetailService.addCartDetail(cartDetailReqDTO);
    }

    @PutMapping
    public CartDetailResDTO updateCartDetail(@RequestBody CartDetailReqDTO cartDetailReqDTO){
        return cartDetailService.updateQty(cartDetailReqDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCartDetail(@PathVariable(name = "id") Long cartDetailId){
        cartDetailService.deleteCartDetail(cartDetailId);
    }
}