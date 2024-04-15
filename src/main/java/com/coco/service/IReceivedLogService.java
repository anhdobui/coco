package com.coco.service;

import com.coco.dto.ReceivedLogDTO;

import java.util.List;

public interface IReceivedLogService {
    ReceivedLogDTO save(ReceivedLogDTO receivedLogDTO);

    List<ReceivedLogDTO> findAll();
}
