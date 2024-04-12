package com.coco.service.impl;

import com.coco.dto.TopicDTO;
import com.coco.entity.TopicEntity;
import com.coco.mapper.TopicMapper;
import com.coco.repository.TopicRepository;
import com.coco.service.ITopicService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService implements ITopicService {

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private TopicRepository topicRepository;

    @Transactional
    @Override
    public TopicDTO save(TopicDTO topicDTO) {
        TopicEntity topicEntity = topicMapper.toEntity(topicDTO);
        if(topicDTO.getId() != null){

        }
        topicEntity = topicRepository.save(topicEntity);
        return topicMapper.toDto(topicEntity);
    }

    @Override
    public List<TopicDTO> getAll() {
        List<TopicEntity> topicEntities = topicRepository.findAll();
        List<TopicDTO> result = topicEntities.stream().map(topicMapper::toDto).collect(Collectors.toList());
        return result;
    }
}
