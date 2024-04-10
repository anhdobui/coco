package com.coco.mapper;

import com.coco.dto.PaintingDTO;
import com.coco.entity.PaintingEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaintingMapper {
    @Autowired
    private ModelMapper modelMapper;

    public PaintingEntity toEntity(PaintingDTO dto){
        PaintingEntity result = modelMapper.map(dto,PaintingEntity.class);
        return result;
    }

    public PaintingDTO toDTO(PaintingEntity entity){
        PaintingDTO result = modelMapper.map(entity,PaintingDTO.class);
        return result;
    }
}
