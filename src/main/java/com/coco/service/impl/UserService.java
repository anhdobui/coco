package com.coco.service.impl;

import com.coco.component.JwtTokenUtils;
import com.coco.dto.*;
import com.coco.entity.AccountEntity;
import com.coco.entity.Role;
import com.coco.entity.UserCustomDetail;
import com.coco.exception.DataNotFoundException;
import com.coco.repository.AccountRepository;
import com.coco.repository.RoleRepository;
import com.coco.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final AccountRepository accountRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ConfirmEmailService confirmEmailService;
    private final JwtTokenUtils jwtTokenUtils;
    @Override
    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        Optional<AccountEntity> userOptional = accountRepository.findByUsername(loginRequest.getEmail());
        if (userOptional.isEmpty()){
            throw new DataNotFoundException("Nguoi dung khong ton tai");
        }
        AccountEntity user = userOptional.get();
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new BadCredentialsException("Mat khau khong chinh xac");
        }
        UserCustomDetail userCustomDetail = new UserCustomDetail(user);
        //Chuyền email,password, role vào authenticationToken để xac thực ngươi dùng
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword(),
                userCustomDetail.getAuthorities()
        );
        //Xác thực người dùng
        authenticationManager.authenticate(authenticationToken);

        String token = jwtTokenUtils.generateToken(userCustomDetail.getAccount());

        LoginResponse loginResponse = LoginResponse.builder()
                .token(token)
                .build();
        return loginResponse;
    }

    @Override
    public String register(AccountDTO accountDTO) throws Exception {
        String email = accountDTO.getEmail();
        if (accountRepository.existsByUsername(email)){
            throw new DataIntegrityViolationException("email đã tồn tại");
        }
        confirmEmailService.sendConfirmEmail(email);
        return "Mã xác minh đã được gửi đến email của bạn";
    }

    @Override
    public void saveUser(AccountDTO accountDTO) throws Exception {
        String email = accountDTO.getEmail();
        if (accountRepository.existsByUsername(email)){
            throw new Exception("email da ton tai");
        }
        Role role = roleRepository.findById(3).orElseThrow(()->new IllegalStateException("Khong tim thay role"));
        AccountEntity newUser = AccountEntity.builder()
                .username(accountDTO.getEmail())
                .password(accountDTO.getPassword())
                .fullname(accountDTO.getFullname())

                .build();
        String password = accountDTO.getPassword();
        String encodePassword = passwordEncoder.encode(password);
        newUser.setPassword(encodePassword);
        accountRepository.save(newUser);
    }

    @Override
    public String changePassword(Long userId, ChangePasswordRequest changePasswordRequest) throws Exception {
        AccountEntity user = accountRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("người dùng không tồn tại"));
        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())){
            return "mật khẩu cũ không chính xác";
        }
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            return "Xác nhận mật khẩu không trùng khớp";
        }
        if (changePasswordRequest.getOldPassword().equals(changePasswordRequest.getNewPassword())){
            return "Mật khẩu cũ và mật khẩu mới không được trùng nhau";
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        accountRepository.save(user);
        return "đổi mật khẩu thành công";
    }

    @Override
    public String forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws Exception {
        AccountEntity user = accountRepository.findByUsername(forgotPasswordRequest.getEmail()).orElseThrow(() -> new DataNotFoundException("người dùng không tồn tại"));
        confirmEmailService.sendConfirmEmail(forgotPasswordRequest.getEmail());
        return "Mã xác nhận đã được gửi vui lòng kiểm tra email";
    }
}
