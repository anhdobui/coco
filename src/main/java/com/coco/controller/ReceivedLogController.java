package com.coco.controller;

import com.coco.dto.ReceivedLogDTO;
import com.coco.service.IReceivedLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/receivedLog")
public class ReceivedLogController {

    @Autowired
    private IReceivedLogService receivedLogService;

    @PostMapping
    public ReceivedLogDTO add(@RequestBody ReceivedLogDTO receivedLogDTO){
        return receivedLogService.save(receivedLogDTO);
    }

    @GetMapping
    public List<ReceivedLogDTO> getAll(){
        return receivedLogService.findAll();
    }
}
