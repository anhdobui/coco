package com.coco.service.impl;

import com.coco.dto.CartPaintingDTO;
import com.coco.dto.CartResDTO;
import com.coco.entity.AccountEntity;
import com.coco.entity.CartDetailEntity;
import com.coco.entity.CartEntity;
import com.coco.entity.PaintingEntity;
import com.coco.exception.CustomRuntimeException;
import com.coco.mapper.CartMapper;
import com.coco.repository.AccountRepository;
import com.coco.repository.CartDetailRepository;
import com.coco.repository.CartRepository;
import com.coco.repository.PaintingRepository;
import com.coco.service.ICartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private PaintingRepository paintingRepository;

    @Autowired
    private CartDetailRepository cartDetailRepository;



    @Transactional
    private CartEntity createCartOfAccount(Long acc_id) {
        CartEntity result = new CartEntity();
        AccountEntity acc = accountRepository.findById(acc_id).orElse(null);
        if(acc != null ){
            result.setAcc(acc);
            return cartRepository.save(result);
        }
        throw new CustomRuntimeException("Tài khoản không tồn tại");
    }

    private CartEntity getCartEntityByAccId(Long acc_id) {
        AccountEntity acc = accountRepository.findById(acc_id).orElse(null);
        if(acc != null ){
            CartEntity cart = cartRepository.findFirstByStatusAndAccIdOrderByCreatedDateDesc(1,acc_id).orElse(null);
            if(cart == null){
                cart = createCartOfAccount(acc_id);
            }
            return cart;
        }
        throw new CustomRuntimeException("Tài khoản không tồn tại");
    }

    @Override
    @Transactional
    public Long addPaintingToCart(CartPaintingDTO cartPaintingDTO) {
        CartEntity cart = getCartEntityByAccId(cartPaintingDTO.getAccountId());
        boolean flagIsExistPainting = false;
        List<CartDetailEntity> cartDetailEntities = new ArrayList<>();
        if(cart.getCartDetails() != null){
            cartDetailEntities = cart.getCartDetails();
            for(CartDetailEntity cartDetail : cartDetailEntities){
                if(cartDetail.getPainting().getId() == cartPaintingDTO.getPaintingId()){
                    flagIsExistPainting = true;
                    break;
                }
            }
        }
       if(!flagIsExistPainting){
           CartDetailEntity cartDetail = new CartDetailEntity();
           PaintingEntity painting = paintingRepository.findById(cartPaintingDTO.getPaintingId()).orElseThrow(() -> new CustomRuntimeException(""));
           cartDetail.setPainting(painting);
           cartDetail.setCart(cart);
           cartDetailEntities.add(cartDetail);
           cart.setCartDetails(cartDetailEntities);
           cartRepository.save(cart);
       }
        Long result = cartDetailRepository.countByCartAccIdAndCartStatus(cartPaintingDTO.getAccountId(),1);
        return result;
    }

    @Override
    public CartResDTO getCartByAccount(Long accountId) {
        CartEntity cart = getCartEntityByAccId(accountId);
        return cartMapper.toResDTO(cart);
    }

}
