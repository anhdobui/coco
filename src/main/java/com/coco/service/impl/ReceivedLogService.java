package com.coco.service.impl;

import com.coco.dto.ReceivedLogDTO;
import com.coco.entity.ReceivedLogEntity;
import com.coco.mapper.ReceivedLogMapper;
import com.coco.repository.PaintingRepository;
import com.coco.repository.ReceivedLogRepository;
import com.coco.service.IReceivedLogService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReceivedLogService implements IReceivedLogService {

    @Autowired
    private ReceivedLogRepository receivedLogRepository;

    @Autowired
    private PaintingRepository paintingRepository;

    @Autowired
    private ReceivedLogMapper receivedLogMapper;
    @Override
    @Transactional
    public ReceivedLogDTO save(ReceivedLogDTO receivedLogDTO) {
        ReceivedLogEntity receivedLogEntity = receivedLogMapper.toEntity(receivedLogDTO);
        receivedLogEntity = receivedLogRepository.save(receivedLogEntity);
        return receivedLogMapper.toDto(receivedLogEntity);
    }

    @Override
    public List<ReceivedLogDTO> findAll() {
        List<ReceivedLogEntity> receivedLogEntities = receivedLogRepository.findAll();
        List<ReceivedLogDTO> result = receivedLogEntities.stream().map(receivedLogMapper::toDto).collect(Collectors.toList());
        return result;
    }
}
