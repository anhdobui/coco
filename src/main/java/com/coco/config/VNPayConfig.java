package com.coco.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class VNPayConfig {
    private String vnp_TmnCode = "K95ZRSZV";
    private String vnp_HashSecret = "EYHDDVZBTXWCCYVLSRHAXFGPGODACXZF";
    private String vnp_Url = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    private String vnp_ReturnUrl = "http://localhost:3000/message";
}
