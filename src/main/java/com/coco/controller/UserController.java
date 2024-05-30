package com.coco.controller;

import com.coco.dto.*;
import com.coco.entity.AccountEntity;
import com.coco.entity.ConfirmEmail;
import com.coco.exception.ConfirmEmailExpired;
import com.coco.exception.DataNotFoundException;
import com.coco.repository.AccountRepository;
import com.coco.repository.ConfirmEmailRepository;
import com.coco.service.impl.ConfirmEmailService;
import com.coco.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class UserController {
    private final UserService userService;
    private final ConfirmEmailService confirmEmailService;
    private final ConfirmEmailRepository confirmEmailRepository;
    private final AccountRepository accountRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        try {
            LoginResponse loginResponse = userService.login(loginRequest);
            return ResponseEntity.ok(loginResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(LoginResponse.builder()
                            .token(null)
                    .build());
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AccountDTO accountDTO){
        try {
            String msg = userService.register(accountDTO);
            return ResponseEntity.ok().body(msg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/confirm-register")
    public ResponseEntity<?> confirmRegister(@RequestParam String confirmCode, @RequestBody AccountDTO accountDTO){
        try {
            boolean isConfirm = confirmEmailService.confirmEmail(confirmCode);
            ConfirmEmail confirmEmail = confirmEmailRepository.findConfirmEmailByCode(confirmCode);
            if (isConfirm){
                userService.saveUser(accountDTO);
                confirmEmailRepository.delete(confirmEmail);
            }
            return ResponseEntity.ok().body("Đăng ký thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
        try {
            AccountEntity user = (AccountEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (accountRepository.findById(user.getId()).isPresent()) {
                System.out.println("Nguoi dung la: " + user.getEmail());
                var result = userService.changePassword(user.getId(), request);
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Không tìm thấy thông tin người dùng.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest){
        try {
            String msg = userService.forgotPassword(forgotPasswordRequest);
            return ResponseEntity.ok().body(msg);
        }catch (DataNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PutMapping("/confirm-new-password")
    public ResponseEntity<?> confirmNewPassword(@RequestBody ConfirmNewPasswordRequest confirmNewPasswordRequest){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        try {
            ConfirmEmail confirmEmail = confirmEmailRepository.findConfirmEmailByCode(confirmNewPasswordRequest.getConfirmCode());
            if (confirmEmail == null) {
                throw new DataNotFoundException("Không tìm thấy email xác nhận cho mã: " + confirmNewPasswordRequest.getConfirmCode());
            }
            Optional<AccountEntity> userOptional = accountRepository.findByEmail(confirmNewPasswordRequest.getEmail());
            if (userOptional == null) {
                throw new DataNotFoundException("Không tìm thấy người dùng cho email: " + confirmNewPasswordRequest.getEmail());
            }
            AccountEntity user = userOptional.get();
            var isConfirm = confirmEmailService.confirmEmail(confirmNewPasswordRequest.getConfirmCode());
            if(isConfirm){
                user.setPassword(passwordEncoder.encode(confirmNewPasswordRequest.getNewPassword()));
                accountRepository.save(user);
                confirmEmail.setAccount(null);
                confirmEmailRepository.delete(confirmEmail);
            }
            return ResponseEntity.ok().body("Tạo mật khẩu mới thành công");
        } catch (DataNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }catch (ConfirmEmailExpired ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
