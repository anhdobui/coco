package com.coco.service;

import com.coco.dto.CartDetailReqDTO;
import com.coco.dto.CartDetailResDTO;

public interface ICartDetailService {
    CartDetailResDTO addCartDetail(CartDetailReqDTO cartDetailReqDTO);

    CartDetailResDTO updateQty(CartDetailReqDTO cartDetailReqDTO);

    void deleteCartDetail(Long cartDetailId);
}
