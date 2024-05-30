package com.coco.service;

public interface IConfirmEmailService {
    void sendConfirmEmail(String email);
    boolean confirmEmail(String confirmCode) throws Exception;
}
