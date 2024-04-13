package com.coco.controller;

import com.coco.dto.TopicDTO;
import com.coco.exception.CustomRuntimeException;
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

    @PutMapping(value="{id}")
    public TopicDTO updateTopic(@RequestBody TopicDTO model,@PathVariable(value = "id") String id){
        try {
            Long idTopic = Long.parseLong(id);
            model.setId(idTopic);
        } catch (Exception e) {
            throw new CustomRuntimeException(e.getMessage());
        }
        return topicService.save(model);
    }

    @DeleteMapping
    public Integer deleteTopic(@RequestBody List<Long> ids){
        return topicService.deleteByIdIn(ids);
    }

    @GetMapping
    private List<TopicDTO> getAll(){
        return topicService.getAll();
    }
}
