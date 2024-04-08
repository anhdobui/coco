package com.coco.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "topic")
public class TopicEntity extends BaseEntity{

    private String title;

    private String description;

    @ManyToMany
    @JoinTable(name = "topic_painting",
            joinColumns = @JoinColumn(name = "topic_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "painting_id", nullable = false))
    private List<PaintingEntity> paintings = new ArrayList<>();
}
