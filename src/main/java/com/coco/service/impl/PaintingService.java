package com.coco.service.impl;

import com.coco.dto.PaintingDTO;
import com.coco.dto.PaintingResDTO;
import com.coco.dto.PaintingSearchDTO;
import com.coco.entity.PaintingEntity;
import com.coco.entity.TopicEntity;
import com.coco.exception.CustomRuntimeException;
import com.coco.mapper.PaintingMapper;
import com.coco.repository.PaintingRepository;
import com.coco.repository.TopicRepository;
import com.coco.service.IPaintingService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public PaintingResDTO findById(Long id) {
        PaintingEntity paintingEntity = paintingRepository.findById(id).orElse(null);
        if(paintingEntity != null){
            return paintingMapper.toResDTO(paintingEntity);
        }
        throw new CustomRuntimeException("Không tồn tại tranh");
    }

    @Override
    public List<PaintingResDTO> findByCondition(PaintingSearchDTO paintingSearch) {
        Specification<PaintingEntity> spec = buildSpecification(paintingSearch);
        List<PaintingEntity> paintings = paintingRepository.findAll(spec);
        List<PaintingResDTO> result = paintings.stream().map(paintingMapper::toResDTO).collect(Collectors.toList());
        return result;
    }
    private Specification<PaintingEntity> buildSpecification(PaintingSearchDTO paintingSearch) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (paintingSearch.getId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("id"), paintingSearch.getId()));
            }
            if (paintingSearch.getCode() != null) {
                predicate = cb.and(predicate, cb.like(root.get("code"), "%" + paintingSearch.getCode() + "%"));
            }
            if (paintingSearch.getName() != null) {
                predicate = cb.and(predicate, cb.like(root.get("name"), "%" + paintingSearch.getName() + "%"));
            }
            if (paintingSearch.getLength() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("length"), paintingSearch.getLength()));
            }
            if (paintingSearch.getWidth() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("width"), paintingSearch.getWidth()));
            }
            if (paintingSearch.getThickness() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("thickness"), paintingSearch.getThickness()));
            }
            if (paintingSearch.getPriceFrom() != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("price"), paintingSearch.getPriceFrom()));
            }
            if (paintingSearch.getPriceTo() != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("price"), paintingSearch.getPriceTo()));
            }
            if (paintingSearch.getTopicIds() != null && !paintingSearch.getTopicIds().isEmpty()) {
                Join<Object, Object> topics = root.join("topics", JoinType.INNER);
                predicate = cb.and(predicate, topics.get("id").in(paintingSearch.getTopicIds()));
            }
            query.orderBy(cb.desc(root.get("modifiedDate")));
            return predicate;
        };
    }
}
