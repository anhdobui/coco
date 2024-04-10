package com.coco.entity;

import com.coco.utils.StringUtils;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "painting")
public class PaintingEntity extends BaseEntity{

    private String code;
    private String name;
    private Double length;
    private Double width;
    private Double thickness;
    private Integer inventory;
    private Double price;
    private String thumbnailUrl;
    @ElementCollection
    @CollectionTable(name = "album", joinColumns = @JoinColumn(name = "painting_id"))
    @Column(name = "url")
    private List<String> albumUrl;

    @ManyToMany(mappedBy = "paintings")
    private List<TopicEntity> topics = new ArrayList<>();

    @OneToMany(mappedBy = "painting",cascade = {CascadeType.PERSIST, CascadeType.MERGE},orphanRemoval = true)
    private List<DetailReceivedLogEntity> detailReceivedLogs;

    @OneToMany(mappedBy = "painting",cascade = {CascadeType.PERSIST, CascadeType.MERGE},orphanRemoval = true)
    private List<CartDetailEntity> cartDetails;


    @PostPersist
    protected void onCreate() {
        if (this.code == null) {
            this.code = StringUtils.extractFirstLetters(getName()) + '-' + getId();
        }
    }

}
