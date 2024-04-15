package com.coco.mapper;

import com.coco.dto.DetailReceivedLogDTO;
import com.coco.entity.DetailReceivedLogEntity;
import com.coco.entity.ReceivedLogEntity;
import com.coco.repository.PaintingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DetailReceivedMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PaintingRepository paintingRepository;

    public DetailReceivedLogEntity toEntity(DetailReceivedLogDTO dto){
        DetailReceivedLogEntity result = modelMapper.map(dto,DetailReceivedLogEntity.class);
        if(dto.getPaintingId() != null)
        result.setPainting(paintingRepository.findById(dto.getPaintingId()).orElse(null));
        return result;
    }

    public DetailReceivedLogEntity toEntity(DetailReceivedLogDTO dto, ReceivedLogEntity receivedLogEntity){
        DetailReceivedLogEntity result = modelMapper.map(dto,DetailReceivedLogEntity.class);
        result.setReceivedLog(receivedLogEntity);
        if(dto.getPaintingId() != null)
            result.setPainting(paintingRepository.findById(dto.getPaintingId()).orElse(null));
        return result;
    }

    public DetailReceivedLogDTO toDTO(DetailReceivedLogEntity entity){
        DetailReceivedLogDTO result = modelMapper.map(entity,DetailReceivedLogDTO.class);
        result.setPaintingId(entity.getPainting().getId());
        result.setPaintingCode(entity.getPainting().getCode());
        return result;
    }
}
