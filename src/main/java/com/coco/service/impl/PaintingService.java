package com.coco.service.impl;

import com.coco.dto.PaintingDTO;
import com.coco.entity.PaintingEntity;
import com.coco.entity.TopicEntity;
import com.coco.exception.CustomRuntimeException;
import com.coco.mapper.PaintingMapper;
import com.coco.repository.PaintingRepository;
import com.coco.repository.TopicRepository;
import com.coco.service.IPaintingService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PaintingService implements IPaintingService {


    @Autowired
    private PaintingMapper paintingMapper;
    @Autowired
    private PaintingRepository paintingRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Transactional
    @Override
    public PaintingDTO save(PaintingDTO paintingDTO) {
        PaintingEntity paintingEntity = paintingMapper.toEntity(paintingDTO);
        if(paintingDTO.getId() != null ){
            PaintingEntity oldPainting = paintingRepository.findById(paintingDTO.getId()).orElse(null);
            if(oldPainting != null){
                paintingEntity.setCode(oldPainting.getCode());
                paintingEntity.setCartDetails(oldPainting.getCartDetails());
                paintingEntity.setDetailReceivedLogs(oldPainting.getDetailReceivedLogs());
                if(paintingDTO.getThumbnailUrl() == null){
                    paintingEntity.setThumbnailUrl(oldPainting.getThumbnailUrl());
                }
                if(paintingDTO.getAlbumUrl() == null){
                    paintingEntity.setAlbumUrl(oldPainting.getAlbumUrl());
                }
            }else{
                throw new CustomRuntimeException("Không tồn tại tranh");
            }
        }
        paintingEntity = paintingRepository.save(paintingEntity);
        if (paintingDTO.getTopicIds() != null){
            Set<TopicEntity> topicEntities = new HashSet<>(topicRepository.findAllById(paintingDTO.getTopicIds()));
            paintingEntity = attachTopic(paintingEntity,topicEntities);
        }
        return paintingMapper.toDTO(paintingEntity);
    }
    @Override
    @Transactional
    public Integer delete(List<Long> ids) {
        if(ids.size() > 0){
            Integer count = paintingRepository.countByIdIn(ids);
            if(count != ids.size()){
                throw new CustomRuntimeException("Sản phẩm không tồn tại");
            }
        }
        return paintingRepository.deleteByIdIn(ids);
    }

    @Transactional
    @Override
    public PaintingEntity attachTopic(PaintingEntity paintingEntity, Set<TopicEntity> topicEntities){
        paintingEntity.setTopics(topicEntities);
        return paintingRepository.save(paintingEntity);
    }

}
