package com.coco.service.impl;

import com.coco.dto.PaintingDTO;
import com.coco.entity.PaintingEntity;
import com.coco.exception.CustomRuntimeException;
import com.coco.mapper.PaintingMapper;
import com.coco.repository.PaintingRepository;
import com.coco.repository.TopicRepository;
import com.coco.service.IPaintingService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
            PaintingEntity oldArtworkEntity = paintingRepository.findById(paintingDTO.getId()).orElse(null);
            if(oldArtworkEntity != null){
                paintingEntity.setCode(oldArtworkEntity.getCode());
                paintingEntity.setCartDetails(oldArtworkEntity.getCartDetails());
                paintingEntity.setDetailReceivedLogs(oldArtworkEntity.getDetailReceivedLogs());
                if(paintingDTO.getThumbnailUrl() == null){
                    paintingEntity.setThumbnailUrl(oldArtworkEntity.getThumbnailUrl());
                }
                if(paintingDTO.getAlbumUrl() == null){
                    paintingEntity.setAlbumUrl(oldArtworkEntity.getAlbumUrl());
                }
            }else{
                throw new CustomRuntimeException("Không tồn tại tranh");
            }
        }
        paintingEntity = paintingRepository.save(paintingEntity);
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

}
