package com.coco.dto;

import lombok.Data;

@Data
public class AccountDTO {
    private Long id;
    private String fullname;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String city;
    private String district;
    private String ward;
    private String pointAddress;
}
