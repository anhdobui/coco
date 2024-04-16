package com.coco.controller;

import com.coco.dto.AccountDTO;
import com.coco.dto.AccountLoginDTO;
import com.coco.dto.AccountResDTO;
import com.coco.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/acc")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @PostMapping
    public AccountResDTO createacc(@RequestBody AccountDTO newAcc){
        return accountService.save(newAcc);
    }

    @PostMapping("/login")
    public AccountResDTO login(@RequestBody AccountLoginDTO acc){
        return accountService.login(acc);
    }
}
