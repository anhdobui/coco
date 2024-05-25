package com.coco.service;

import com.coco.dto.AccountDTO;
import com.coco.dto.AccountFilterDTO;
import com.coco.dto.AccountLoginDTO;
import com.coco.dto.AccountResDTO;

import java.util.List;

public interface IAccountService {
    AccountResDTO save(AccountDTO acc);

    AccountResDTO login(AccountLoginDTO acc);

    List<AccountResDTO> getByCondition(AccountFilterDTO accountFilterDTO);
}
