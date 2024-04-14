package com.coco.service;

import com.coco.dto.TopicDTO;
import com.coco.dto.TopicResDTO;

import java.util.List;

public interface ITopicService {
    TopicDTO save(TopicDTO topicDTO);

    List<TopicDTO> getAll();

    Integer deleteByIdIn(List<Long> ids);

    TopicResDTO getById(Long id);
}
