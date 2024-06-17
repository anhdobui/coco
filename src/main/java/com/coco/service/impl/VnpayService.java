package com.coco.service.impl;

import com.coco.config.VNPayConfig;
import com.coco.dto.OrderReqDTO;
import com.coco.entity.CartDetailEntity;
import com.coco.entity.CartEntity;
import com.coco.entity.OrdersEntity;
import com.coco.repository.CartDetailRepository;
import com.coco.repository.CartRepository;
import com.coco.repository.OrdersRepository;
import com.coco.utils.VNPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VnpayService {

    @Autowired
    private VNPayConfig vnpayConfig;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    CartDetailRepository cartDetailRepository;

    public String createPaymentUrl(OrderReqDTO orderReqDTO) throws URISyntaxException, UnsupportedEncodingException {
        CartEntity cart = cartRepository.findById(orderReqDTO.getCartId()).orElse(null);
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }
        double totalMonney = orderReqDTO.getShippingCost();
        for (CartDetailEntity cartDetail : cart.getCartDetails()) {
            double totalPricePainting = cartDetail.getQty() * cartDetail.getPainting().getPrice();
            totalMonney += totalPricePainting;
        }
        long totalAmount = (long) (totalMonney * 100); // Ensure the amount is an integer

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnpayConfig.getVnp_TmnCode());
        vnp_Params.put("vnp_Amount", String.valueOf(totalAmount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", String.valueOf(orderReqDTO.getCartId()));
        vnp_Params.put("vnp_OrderInfo", "Payment for order: " + orderReqDTO.getCartId());
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnpayConfig.getVnp_ReturnUrl());
        vnp_Params.put("vnp_IpAddr", "127.0.0.1");
        vnp_Params.put("vnp_CreateDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                try {
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = hmacSHA512(vnpayConfig.getVnp_HashSecret(), hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = vnpayConfig.getVnp_Url() + "?" + queryUrl;
        return paymentUrl;
    }



    private String hmacSHA512(final String key, final String data) {
        try {
            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (Exception ex) {
            return "";
        }
    }

    public OrdersEntity findOrderById(Long id) {
        return ordersRepository.findById(id).orElse(null);
    }

    public void saveOrder(OrdersEntity order) {
        ordersRepository.save(order);
    }

    public CartEntity findCartById(Long id){
        return cartRepository.findById(id).orElse(null);
    }

    public void saveCart(CartEntity cart){
        cartRepository.save(cart);
    }
}
