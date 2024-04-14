package com.coco.controller;

import com.coco.cloudinary.CloudinaryService;
import com.coco.dto.PaintingDTO;
import com.coco.dto.PaintingResDTO;
import com.coco.dto.PaintingSearchDTO;
import com.coco.entity.PaintingEntity;
import com.coco.exception.CustomRuntimeException;
import com.coco.repository.PaintingRepository;
import com.coco.service.IPaintingService;
import com.coco.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/painting")
public class PaintingController {

    @Autowired
    private IPaintingService paintingService;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private PaintingRepository paintingRepository;
    @PostMapping
    public PaintingDTO addPainting(@ModelAttribute PaintingDTO model, @RequestParam(name = "thumbnail" ,required = false) MultipartFile thumbnail, @RequestParam(name = "album",required = false) MultipartFile[] album) throws IOException {
        if(album != null){
            model.setAlbumUrl(Arrays.stream(album).map(image -> {
                try {
                    return cloudinaryService.uploadFile(image, StringUtils.rmFileExtension(image.getOriginalFilename()));
                } catch (IOException e) {
                    throw new CustomRuntimeException(e.getMessage());
                }
            }).collect(Collectors.toList()));
        }
        if(thumbnail != null)
        model.setThumbnailUrl(cloudinaryService.uploadFile(thumbnail,StringUtils.rmFileExtension(thumbnail.getOriginalFilename())));
        model.setId(null);
        return paintingService.save(model);
    }
    @PutMapping(value="{id}")
    public PaintingDTO updatePainting(@ModelAttribute PaintingDTO model, @RequestParam(name = "thumbnail",required = false) MultipartFile thumbnail,@RequestParam(name = "album",required = false) MultipartFile[] album,@PathVariable(value = "id") String id) throws Exception {
        try {
            Long idPt = Long.parseLong(id);
            model.setId(idPt);
            if(thumbnail != null){
                model.setThumbnailUrl(cloudinaryService.uploadFile(thumbnail,StringUtils.rmFileExtension(thumbnail.getOriginalFilename())));
            }
            if(album != null){
                model.setAlbumUrl(Arrays.stream(album).map(image -> {
                    try {
                        return cloudinaryService.uploadFile(image,
                                StringUtils.rmFileExtension(image.getOriginalFilename()));
                    } catch (IOException e) {
                        throw new CustomRuntimeException(e.getMessage());
                    }
                }).collect(Collectors.toList()));
            }
        } catch (Exception e) {
            throw new CustomRuntimeException(e.getMessage());
        }
        return paintingService.save(model);
    }

    @PostMapping("/test")
    public PaintingEntity posP(@RequestBody PaintingEntity paintingEntity){
        return paintingRepository.save(paintingEntity);
    }

    @DeleteMapping
    public Integer deletePainting(@RequestBody List<Long> ids){
       return paintingService.delete(ids);
    }


    @GetMapping
    public List<PaintingResDTO> getByCondition(@ModelAttribute PaintingSearchDTO paintingSearch){
        return paintingService.findByCondition(paintingSearch);
    }

    @GetMapping(value = "{id}")
    public PaintingResDTO getDetail(@PathVariable("id") Long id ){
        return paintingService.findById(id);
    }

}
