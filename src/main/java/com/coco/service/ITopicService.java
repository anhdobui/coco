package com.coco.service;

import com.coco.dto.TopicDTO;

import java.util.List;

public interface ITopicService {
    TopicDTO save(TopicDTO topicDTO);

    List<TopicDTO> getAll();

    Integer deleteByIdIn(List<Long> ids);
}
