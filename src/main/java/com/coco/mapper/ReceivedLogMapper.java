package com.coco.mapper;

import com.coco.dto.DetailReceivedLogDTO;
import com.coco.dto.ReceivedLogDTO;
import com.coco.entity.DetailReceivedLogEntity;
import com.coco.entity.ReceivedLogEntity;
import com.coco.repository.PaintingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReceivedLogMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PaintingRepository paintingRepository;

    @Autowired
    private DetailReceivedMapper detailReceivedMapper;

    public ReceivedLogEntity toEntity(ReceivedLogDTO dto){
        ReceivedLogEntity result = modelMapper.map(dto,ReceivedLogEntity.class);
        List<DetailReceivedLogEntity> detailReceivedLogEntities =  dto.getDetail().stream().map(item ->detailReceivedMapper.toEntity(item,result)).collect(Collectors.toList());
        result.setDetailReceivedLogs(detailReceivedLogEntities);
        return result;
    }

    public ReceivedLogDTO toDto(ReceivedLogEntity entity){
        ReceivedLogDTO result = modelMapper.map(entity,ReceivedLogDTO.class);
        List<DetailReceivedLogDTO> detailReceivedLogDTOs = entity.getDetailReceivedLogs().stream().map(detailReceivedMapper::toDTO).collect(Collectors.toList());
        result.setDetail(detailReceivedLogDTOs);
        return result;
    }
}
