package com.coco.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dzbiwncwe",
                "api_key", "582978748519645",
                "api_secret", "GHgYzVNJljmXSeI2W3KsyW6y5Wo",
                "secure", true
        ));
        return cloudinary;
    }
}
