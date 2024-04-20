package com.coco.service;

import com.coco.dto.CartPaintingDTO;
import com.coco.dto.CartResDTO;

public interface ICartService {
    Long addPaintingToCart(CartPaintingDTO cartPaintingDTO);

    CartResDTO getCartByAccount(Long accountId);
}
