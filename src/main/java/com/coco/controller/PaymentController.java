package com.coco.controller;

import com.coco.dto.OrderReqDTO;
import com.coco.entity.CartEntity;
import com.coco.entity.OrdersEntity;
import com.coco.service.impl.VnpayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private VnpayService vnpayService;

    @GetMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody OrderReqDTO orderReqDTO) {
        try {
            String paymentUrl = vnpayService.createPaymentUrl(orderReqDTO);
            return ResponseEntity.ok(Collections.singletonMap("paymentUrl", paymentUrl));
        } catch (URISyntaxException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/vnpay_return")
    public ResponseEntity<?> handleVnpayReturn(@RequestParam Map<String, String> allParams) {
        //Mã giao dịch trả về từ vnpay
        String vnp_TxnRef = allParams.get("vnp_TxnRef");
        //Mã phản hồi từ vnpay, 00 chỉ rằng đã thanh toán thành công
        String vnp_ResponseCode = allParams.get("vnp_ResponseCode");
        CartEntity cart = vnpayService.findCartById(Long.parseLong(vnp_TxnRef));
        OrdersEntity order = vnpayService.findOrderById(cart.getOrders().getId());
        if (vnp_ResponseCode.equals("00")) {
            if (order != null && cart != null) {
                //Cập nhật trạng thái order
                order.setPaymentStatus(0);
                vnpayService.saveOrder(order);

                //Cập nhật trạng thái giỏ hàng
                cart.setStatus(0);
                vnpayService.saveCart(cart);
                return ResponseEntity.ok("Thanh toán thành công: " + vnp_TxnRef);
            } else {
                return ResponseEntity.status(404).body("Không tìm thấy đơn hàng");
            }
        } else {
            return ResponseEntity.status(400).body("Thanh toán thất bại: " + vnp_ResponseCode);
        }
    }
}
