package com.coco.service.impl;

import com.coco.dto.OrderFilterDTO;
import com.coco.dto.OrderReqDTO;
import com.coco.dto.OrdersDTO;
import com.coco.entity.AccountEntity;
import com.coco.entity.CartEntity;
import com.coco.entity.OrdersEntity;
import com.coco.enumDefine.StatusOrderEnum;
import com.coco.enumDefine.StatusPaymentEnum;
import com.coco.exception.CustomRuntimeException;

import com.coco.mapper.CartMapper;

import com.coco.mapper.OrdersMapper;
import com.coco.repository.AccountRepository;
import com.coco.repository.CartRepository;
import com.coco.repository.OrdersRepository;
import com.coco.service.IOrdersService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersService implements IOrdersService {


    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private CartMapper cartMapper;


    @Override
    @Transactional
    public OrdersDTO orderPaintings(OrderReqDTO ordReq) {
        if(ordersRepository.existsByCart_Id(ordReq.getCartId()))
            throw new CustomRuntimeException("Đơn hàng đã cũ");
        if(checkCartOfAcc(ordReq)){
            if(!checkCountCartDetailOfCart(ordReq.getCartId())){
                throw new CustomRuntimeException("Giỏ hàng chưa có sản phẩm nào");
            }
            OrdersEntity ordersEntity = new OrdersEntity();
            CartEntity cart = cartRepository.findById(ordReq.getCartId()).orElseThrow(() -> new CustomRuntimeException("Giỏ hàng không tồn tại"));
            ordersEntity.setCart(cart);
            ordersEntity.setShippingCost(ordReq.getShippingCost());
            ordersEntity = ordersRepository.save(ordersEntity);
            return ordersMapper.toDTO(ordersEntity);
        }
        throw new CustomRuntimeException("Lỗi giỏ hàng");
    }

    @Override

    public List<OrdersDTO> getByCondition(OrderFilterDTO orderFilterDTO) {
        List<OrdersEntity> orders = ordersRepository.findAll(filterByCondition(orderFilterDTO));
        List<OrdersDTO> resut = orders.stream().map(ordersMapper::toDTO).collect(Collectors.toList());
        return resut;
    }

    @Override
    public OrdersDTO updateStatus(Integer newStatus, Long id) {
        OrdersEntity order = ordersRepository.findById(id)
                .orElseThrow(() -> new CustomRuntimeException("Đơn hàng không tồn tại"));

        Integer currentStatus = order.getStatus();

        // Kiểm tra trạng thái hiện tại và ném ngoại lệ nếu cần thiết
        if (currentStatus == 0) {
            throw new CustomRuntimeException("Đơn đã hủy");
        }
        if (currentStatus == 3) {
            throw new CustomRuntimeException("Đơn hàng đã hoàn thành");
        }
        if (currentStatus == 2 && newStatus == 1) {
            throw new CustomRuntimeException("Không thể cập nhật đơn hàng về trạng thái này");
        }
        if(newStatus == 3){
            AccountEntity accountEntity = order.getCart().getAcc();
            accountEntity.setGrade(calculationGrade(cartMapper.calculationTotalCart(order.getCart()), accountEntity.getGrade()));
            accountRepository.save(accountEntity);
        }
        // Cập nhật trạng thái nếu các điều kiện hợp lệ
        order.setStatus(newStatus);
        order = ordersRepository.save(order);
        return ordersMapper.toDTO(order);
    }

    @Override
    @Transactional
    public void updateVnpayUrl(Long orderId,String url) {
       OrdersEntity ordersEntity = ordersRepository.findById(orderId).orElseThrow(()-> new CustomRuntimeException("Order not found"));
        ordersEntity.setVnpayUrl(url);
        ordersRepository.save(ordersEntity);
    }

    private Double calculationGrade(BigDecimal totalCart,Double oldGrade){
        BigDecimal newGrade = totalCart.divide(new BigDecimal("10")).add(new BigDecimal(oldGrade));
        Double result = newGrade.setScale(2, RoundingMode.HALF_UP).doubleValue();
        return result;
    }
    private Specification<OrdersEntity> filterByCondition(OrderFilterDTO filter) {
        return (Root<OrdersEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (filter.getAccountId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("cart").get("acc").get("id"), filter.getAccountId()));
            }
            if (filter.getOrderStatus() != null) {
                StatusOrderEnum statusEnum = StatusOrderEnum.fromString(filter.getOrderStatus());
                Integer status = statusEnum.getValue();
                predicate = cb.and(predicate, cb.equal(root.get("status"), status));
            }
            if (filter.getPaymentStatus() != null) {
                StatusPaymentEnum statusPayment = StatusPaymentEnum.fromString(filter.getPaymentStatus());
                predicate = cb.and(predicate, cb.equal(root.get("paymentStatus"), statusPayment.getValue()));
            }
            if (filter.getStartOrderDate() != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("orderDate"), filter.getStartOrderDate()));
            }
            query.orderBy(cb.desc(root.get("orderDate")));
            return predicate;
        };
    }
    private boolean checkCountCartDetailOfCart(Long cartId){
        return cartRepository.hasCartDetails(cartId);
    }
    private boolean checkCartOfAcc(OrderReqDTO ordReq){
        CartEntity cart = cartService.getCartEntityByAccId(ordReq.getAccountId());
        if(cart != null){
            if (cart.getId() == ordReq.getCartId())
                return true;

        }

        return false;
    }
}
