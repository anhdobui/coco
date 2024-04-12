package com.coco.mapper;

import com.coco.dto.PaintingDTO;
import com.coco.entity.PaintingEntity;
import com.coco.entity.TopicEntity;
import com.coco.repository.TopicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PaintingMapper {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TopicRepository topicRepository;

    public PaintingEntity toEntity(PaintingDTO dto){
        PaintingEntity result = modelMapper.map(dto,PaintingEntity.class);
        if (dto.getTopicIds() != null){
            Set<TopicEntity> topicEntities = new HashSet<>(topicRepository.findAllById(dto.getTopicIds()));
            result.setTopics(topicEntities);
        }
        return result;
    }

    public PaintingDTO toDTO(PaintingEntity entity){
        PaintingDTO result = modelMapper.map(entity,PaintingDTO.class);
        if (entity.getTopics() != null)
        result.setTopicIds(entity.getTopics().stream().map(TopicEntity::getId).collect(Collectors.toList()));
        return result;
    }
}
