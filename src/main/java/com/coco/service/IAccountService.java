package com.coco.service;

import com.coco.dto.AccountDTO;
import com.coco.dto.AccountLoginDTO;
import com.coco.dto.AccountResDTO;

public interface IAccountService {
    AccountResDTO save(AccountDTO acc);

    AccountResDTO login(AccountLoginDTO acc);
}
