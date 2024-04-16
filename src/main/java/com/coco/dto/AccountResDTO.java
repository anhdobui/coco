package com.coco.dto;

import lombok.Data;

@Data
public class AccountResDTO {
    private Long id;
    private String fullname;
    private String username;
    private Double grade;
    private String email;
    private String phone;
}
