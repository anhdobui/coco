package com.coco.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "topic")
public class TopicEntity extends BaseEntity{

    private String title;

    private String description;

    @ManyToMany(targetEntity = PaintingEntity.class,mappedBy = "topics",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    Set<PaintingEntity> paintings;
}
