package com.coco.service.impl;

import com.coco.dto.CartDetailReqDTO;
import com.coco.dto.CartDetailResDTO;
import com.coco.entity.CartDetailEntity;
import com.coco.entity.CartEntity;
import com.coco.entity.PaintingEntity;
import com.coco.exception.CustomRuntimeException;
import com.coco.mapper.CartDetailMapper;
import com.coco.repository.CartDetailRepository;
import com.coco.repository.CartRepository;
import com.coco.repository.PaintingRepository;
import com.coco.service.ICartDetailService;
import com.coco.service.ICartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartDetailService implements ICartDetailService {

    @Autowired
    private ICartService cartService;
    @Autowired
    private PaintingRepository paintingRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartDetailRepository cartDetailRepository;
    @Autowired
    private CartDetailMapper cartDetailMapper;
    @Override
    @Transactional
    public CartDetailResDTO addCartDetail(CartDetailReqDTO cartDetailReqDTO) {
        CartEntity cart = cartService.getCartEntityByAccId(cartDetailReqDTO.getAccountId());
        CartDetailEntity cartDetailRes = null;
        boolean flagIsExistPainting = false;
        List<CartDetailEntity> cartDetailEntities = new ArrayList<>();
        if(cart.getCartDetails() != null){
            cartDetailEntities = cart.getCartDetails();
            for(CartDetailEntity cartDetail : cartDetailEntities){
                if(cartDetail.getPainting().getId() == cartDetailReqDTO.getPaintingId()){
                    cartDetailRes = cartDetail;
                    flagIsExistPainting = true;
                    break;
                }
            }
        }
        if(!flagIsExistPainting){
            cartDetailRes =new CartDetailEntity();
            PaintingEntity painting = paintingRepository.findById(cartDetailReqDTO.getPaintingId()).orElseThrow(() -> new CustomRuntimeException("Tranh không tồn tại"));
            cartDetailRes.setPainting(painting);
            cartDetailRes.setCart(cart);
            cartDetailEntities.add(cartDetailRes);
            cart.setCartDetails(cartDetailEntities);
            cartDetailRes = cartDetailRepository.save(cartDetailRes);
            cartRepository.save(cart);
        }
        CartDetailResDTO result = cartDetailMapper.toResDTO(cartDetailRes,cart);
        return result;
    }

    @Override
    @Transactional
    public CartDetailResDTO updateQty(CartDetailReqDTO cartDetailReqDTO) {
        CartDetailEntity cartDetailEntity = cartDetailRepository.findById(cartDetailReqDTO.getId()).orElseThrow(() -> new CustomRuntimeException("Chi tiết giỏ hàng này không tồn tại"));
        cartDetailEntity.setQty(cartDetailReqDTO.getQty());
        cartDetailEntity = cartDetailRepository.save(cartDetailEntity);
        CartDetailResDTO result = cartDetailMapper.toResDTO(cartDetailEntity);
        return result;
    }

    @Override
    @Transactional
    public void deleteCartDetail(Long cartDetailId) {
        CartDetailEntity cartDetailEntity = cartDetailRepository.findById(cartDetailId).orElseThrow(() -> new CustomRuntimeException("Chi tiết giỏ hàng này không tồn tại"));
        cartDetailRepository.delete(cartDetailEntity);
    }
}
