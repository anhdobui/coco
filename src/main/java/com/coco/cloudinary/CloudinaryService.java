package com.coco.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.coco.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }
    public String uploadFile(MultipartFile file,String desiredFileName) throws IOException {
        if(StringUtils.isNullOrEmpty(desiredFileName))  desiredFileName = "no-name";
        Map uploadResult =cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "public_id", desiredFileName,
                "overwrite", true
        ));
        return (String) uploadResult.get("url");
    }
}
