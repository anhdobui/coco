package com.coco.controller;

import com.coco.dto.TopicDTO;
import com.coco.service.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/topic")
public class TopicController {

    @Autowired
    private ITopicService topicService;
    @PostMapping
    public TopicDTO addTopic(@RequestBody TopicDTO model){
        model.setId(null);
        return topicService.save(model);
    }

    @GetMapping
    private List<TopicDTO> getAll(){
        return topicService.getAll();
    }
}
