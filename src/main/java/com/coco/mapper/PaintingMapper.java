package com.coco.mapper;

import com.coco.dto.PaintingDTO;
import com.coco.dto.PaintingResDTO;
import com.coco.entity.PaintingEntity;
import com.coco.entity.TopicEntity;
import com.coco.repository.TopicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PaintingMapper {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TopicRepository topicRepository;

    public PaintingEntity toEntity(PaintingDTO dto){
        PaintingEntity result = modelMapper.map(dto,PaintingEntity.class);
        return result;
    }

    public PaintingDTO toDTO(PaintingEntity entity){
        PaintingDTO result = modelMapper.map(entity,PaintingDTO.class);
        if (entity.getTopics() != null)
        result.setTopicIds(entity.getTopics().stream().map(TopicEntity::getId).collect(Collectors.toList()));
        return result;
    }

    public PaintingResDTO toResDTO(PaintingEntity entity){
        PaintingResDTO result = modelMapper.map(entity,PaintingResDTO.class);
        return result;
    }

}
