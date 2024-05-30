package com.coco.service;

import com.coco.dto.*;

public interface IUserService {
    LoginResponse login(LoginRequest loginRequest) throws Exception;
    String register(AccountDTO accountDTO) throws Exception;
    void saveUser(AccountDTO accountDTO) throws Exception;
    String changePassword(Long userId, ChangePasswordRequest changePasswordRequest) throws Exception;
    String forgotPassword(ForgotPasswordRequest forgotPasswordRequest)throws Exception;
}
