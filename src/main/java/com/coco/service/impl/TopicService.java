package com.coco.service.impl;

import com.coco.dto.TopicDTO;
import com.coco.dto.TopicResDTO;
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
            Set<TopicEntity> topicEntities = new HashSet<>(topicRepository.findAllById(ids));
            if(topicEntities.size() != ids.size()){
                throw new CustomRuntimeException("Có Topic không tồn tại");
            }else{
                Set<PaintingEntity> paintingEntities = paintingRepository.findByTopicsIn(topicEntities);
                paintingEntities.stream().forEach(painting ->{
                    Set<TopicEntity> topicUpdate =  painting.getTopics();
                    topicUpdate.removeAll(topicEntities);
                    paintingService.attachTopic(painting,topicUpdate);
                });
            }
        }
        return topicRepository.deleteByIdIn(ids);
    }

    @Override
    public TopicResDTO getById(Long id) {
        TopicEntity topicEntity = topicRepository.findById(id).orElse(null);
        if(topicEntity != null){
            return topicMapper.toResDto(topicEntity);
        }
        throw new CustomRuntimeException("Topic không tồn tại");
    }

}
