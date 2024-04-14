package com.coco.utils;

import com.coco.dto.PaintingSearchDTO;
import com.coco.entity.PaintingEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

public class PaintingUtils {
    public static Example<PaintingEntity> createExample(PaintingSearchDTO searchDTO) {
        PaintingEntity entity = new PaintingEntity();
        entity.setCode(searchDTO.getCode());
        entity.setName(searchDTO.getName());
        entity.setLength(searchDTO.getLength());
        entity.setWidth(searchDTO.getWidth());
        entity.setThickness(searchDTO.getThickness());


        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("length", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("width", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("thickness", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnoreNullValues();

        return Example.of(entity, matcher);
    }
}
