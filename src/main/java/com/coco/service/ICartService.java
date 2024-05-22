package com.coco.service;

import com.coco.dto.CartPaintingDTO;
import com.coco.dto.CartResDTO;
import com.coco.entity.CartEntity;

public interface ICartService {
    CartEntity getCartEntityByAccId(Long acc_id);

    Long addPaintingToCart(CartPaintingDTO cartPaintingDTO);

    CartResDTO getCartByAccount(Long accountId);

}
