package com.coco.service.impl;

import com.coco.dto.TopicDTO;
import com.coco.entity.PaintingEntity;
import com.coco.entity.TopicEntity;
import com.coco.exception.CustomRuntimeException;
import com.coco.mapper.TopicMapper;
import com.coco.repository.PaintingRepository;
import com.coco.repository.TopicRepository;
import com.coco.service.IPaintingService;
import com.coco.service.ITopicService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TopicService implements ITopicService {

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private PaintingRepository paintingRepository;

    @Autowired
    private IPaintingService paintingService;

    @Transactional
    @Override
    public TopicDTO save(TopicDTO topicDTO) {
        TopicEntity topicEntity = topicMapper.toEntity(topicDTO);
        if(topicDTO.getId() != null){
            TopicEntity oldTopic = topicRepository.findById(topicDTO.getId()).orElse(null);
            if(oldTopic != null){
                oldTopic.getPaintings().stream().forEach(painting ->{
                    Set<TopicEntity> oldTopics = painting.getTopics();
                    oldTopics.remove(oldTopic);
                    paintingService.attachTopic(painting,oldTopics);
                });
            }
        }
        topicEntity = topicRepository.save(topicEntity);
        if(topicDTO.getPaintingIds() != null){
            Set<PaintingEntity> paintingEntities = new HashSet<>(paintingRepository.findAllById(topicDTO.getPaintingIds()));
            for (PaintingEntity painting : paintingEntities){
                Set<TopicEntity> topics = painting.getTopics();
                topics.add(topicEntity);
                paintingService.attachTopic(painting,topics);
            }
            topicEntity.setPaintings(paintingEntities);
        }
        return topicMapper.toDto(topicEntity);
    }

    @Override
    public List<TopicDTO> getAll() {
        List<TopicEntity> topicEntities = topicRepository.findAll();
        List<TopicDTO> result = topicEntities.stream().map(topicMapper::toDto).collect(Collectors.toList());
        return result;
    }

    @Override
    @Transactional
    public Integer deleteByIdIn(List<Long> ids) {
        if(ids.size() > 0){
            Integer count = topicRepository.countByIdIn(ids);
            if(count != ids.size()){
                throw new CustomRuntimeException("Topic không tồn tại");
            }
        }
        return topicRepository.deleteByIdIn(ids);
    }

}
