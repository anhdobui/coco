package com.coco.controller;

import com.coco.dto.OrderReqDTO;
import com.coco.dto.OrdersDTO;
import com.coco.entity.OrdersEntity;
import com.coco.exception.CustomRuntimeException;
import com.coco.repository.OrdersRepository;
import com.coco.service.IOrdersService;
import com.coco.service.impl.VnpayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/vnpay")
public class PaymentController {

    @Autowired
    private VnpayService vnpayService;

    @Autowired
    private IOrdersService ordersService;
    @Autowired
    private OrdersRepository ordersRepository;
    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody OrderReqDTO orderReqDTO) {
        try {
            OrdersDTO ordersDTO = ordersService.orderPaintings(orderReqDTO);
            String paymentUrl = vnpayService.createPaymentUrl(ordersDTO);
            ordersService.updateVnpayUrl(ordersDTO.getId(),paymentUrl);
            return ResponseEntity.ok(Collections.singletonMap("paymentUrl", paymentUrl));
        } catch (URISyntaxException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/ipn")
    public ResponseEntity<String> handleVNPayIPN(@RequestParam Map<String, String> allParams) {
        String vnp_TxnRef = allParams.get("vnp_TxnRef");
        String vnp_ResponseCode = allParams.get("vnp_ResponseCode");

        OrdersEntity ordersEntity = ordersRepository.findById(Long.parseLong(vnp_TxnRef))
                .orElseThrow(() -> new CustomRuntimeException("Order not found"));

        if ("00".equals(vnp_ResponseCode)) {
            ordersEntity.setPaymentStatus(1);
            ordersEntity.setVnpayUrl(null);
            ordersRepository.save(ordersEntity);
            return ResponseEntity.ok("success");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid payment response code");
        }
    }
    @GetMapping("/vnpay_return")
    public ResponseEntity<?> handleVnpayReturn(@RequestParam Map<String, String> allParams) {
        //Mã giao dịch trả về từ vnpay
        String vnp_TxnRef = allParams.get("vnp_TxnRef");
        //Mã phản hồi từ vnpay, 00 chỉ rằng đã thanh toán thành công
        String vnp_ResponseCode = allParams.get("vnp_ResponseCode");
        OrdersEntity ordersEntity = ordersRepository.findById(Long.parseLong(vnp_TxnRef)).orElseThrow(() -> new CustomRuntimeException("Order not found"));
        if (vnp_ResponseCode.equals("00")) {
            if (ordersEntity != null) {
                //Cập nhật trạng thái order
                ordersEntity.setPaymentStatus(1);
                ordersEntity.setVnpayUrl(null);
                ordersRepository.save(ordersEntity);
                //Cập nhật trạng thái giỏ hàng
                return ResponseEntity.ok("Thanh toán thành công: " + vnp_TxnRef);
            } else {
                return ResponseEntity.status(404).body("Không tìm thấy đơn hàng");
            }
        } else {
            return ResponseEntity.status(400).body("Thanh toán thất bại: " + vnp_ResponseCode);
        }
    }
}
