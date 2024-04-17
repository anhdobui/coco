package com.coco.service;

import com.coco.dto.CartPaintingDTO;
import com.coco.dto.CartResDTO;

public interface ICartService {
    Integer addPaintingToCart(CartPaintingDTO cartPaintingDTO);

    CartResDTO getCartByAccount(Long accountId);
}
