package com.coco.dto;

import lombok.Data;

@Data
public class AccountFilterDTO {
    private String fullname;
    private String username;
    private String email;
    private String phone;
    private String city;
    private String district;
    private String ward;
    private Double minGrade;
    private Double maxGrade;
}
