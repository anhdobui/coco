package com.coco.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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

    @ManyToMany(mappedBy = "topics",fetch = FetchType.LAZY)
    Set<PaintingEntity> paintings;
}
