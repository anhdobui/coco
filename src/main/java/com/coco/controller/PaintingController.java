package com.coco.controller;

import com.coco.cloudinary.CloudinaryService;
import com.coco.dto.PaintingDTO;
import com.coco.exception.CustomRuntimeException;
import com.coco.service.IPaintingService;
import com.coco.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/painting")
public class PaintingController {

    @Autowired
    private IPaintingService paintingService;
    @Autowired
    private CloudinaryService cloudinaryService;
    @PostMapping
    public PaintingDTO addPainting(@ModelAttribute PaintingDTO model, @RequestParam("thumbnail") MultipartFile thumbnail, @RequestParam("album") MultipartFile[] album) throws IOException {
        model.setAlbumUrl(Arrays.stream(album).map(image -> {
            try {
                return cloudinaryService.uploadFile(image, StringUtils.rmFileExtension(image.getOriginalFilename()));
            } catch (IOException e) {
                throw new CustomRuntimeException(e.getMessage());
            }
        }).collect(Collectors.toList()));
        model.setThumbnailUrl(cloudinaryService.uploadFile(thumbnail,StringUtils.rmFileExtension(thumbnail.getOriginalFilename())));
        model.setId(null);
        return paintingService.save(model);
    }
}
