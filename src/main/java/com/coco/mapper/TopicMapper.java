package com.coco.mapper;

import com.coco.dto.TopicDTO;
import com.coco.dto.TopicResDTO;
import com.coco.entity.PaintingEntity;
import com.coco.entity.TopicEntity;
import com.coco.repository.PaintingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TopicMapper {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PaintingRepository paintingRepository;

    public TopicEntity toEntity(TopicDTO topicDTO){
        TopicEntity result = modelMapper.map(topicDTO,TopicEntity.class);
        return result;
    }

    public TopicDTO toDto(TopicEntity topicEntity){
        TopicDTO result = modelMapper.map(topicEntity,TopicDTO.class);
        if(topicEntity.getPaintings() != null)
        result.setPaintingIds(topicEntity.getPaintings().stream().map(PaintingEntity::getId).collect(Collectors.toList()));
        return result;
    }

    public TopicResDTO toResDto(TopicEntity topicEntity) {
        TopicResDTO result = modelMapper.map(topicEntity,TopicResDTO.class);
        return result;
    }
}
