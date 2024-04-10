package com.coco.service.impl;

import com.coco.dto.PaintingDTO;
import com.coco.entity.PaintingEntity;
import com.coco.exception.CustomRuntimeException;
import com.coco.mapper.PaintingMapper;
import com.coco.repository.PaintingRepository;
import com.coco.service.IPaintingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaintingService implements IPaintingService {

    @Autowired
    private PaintingMapper paintingMapper;
    @Autowired
    private PaintingRepository paintingRepository;

    @Override
    public PaintingDTO save(PaintingDTO paintingDTO) {
        PaintingEntity paintingEntity =  new PaintingEntity();
        if(paintingDTO.getId() != null ){
            PaintingEntity oldArtworkEntity = paintingRepository.findById(paintingDTO.getId()).orElse(null);
            if(oldArtworkEntity != null){
                paintingEntity = paintingMapper.toEntity(paintingDTO);
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
        }else{
            paintingEntity = paintingMapper.toEntity(paintingDTO);
        }
        paintingEntity = paintingRepository.save(paintingEntity);
        return paintingMapper.toDTO(paintingEntity);
    }
}
