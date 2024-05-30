package com.coco.dto;

import lombok.Data;

@Data
public class ConfirmNewPasswordRequest {
    private String email;
    private String confirmCode;
    private String newPassword;
}
