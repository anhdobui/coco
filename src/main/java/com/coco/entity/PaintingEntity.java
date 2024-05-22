package com.coco.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "painting")
@AllArgsConstructor
@NoArgsConstructor
public class PaintingEntity extends BaseEntity{

    private String code;
    private String name;
    private String artist;
    private Double length;
    private Double width;
    private Double thickness;
    private Integer inventory ;
    private Double price;
    private String thumbnailUrl;
    @ElementCollection
    @CollectionTable(name = "album", joinColumns = @JoinColumn(name = "painting_id"))
    @Column(name = "url")
    private List<String> albumUrl;



    @OneToMany(mappedBy = "painting",cascade = {CascadeType.PERSIST, CascadeType.MERGE},orphanRemoval = true)
    private List<DetailReceivedLogEntity> detailReceivedLogs;

    @OneToMany(mappedBy = "painting",cascade = {CascadeType.PERSIST, CascadeType.MERGE},orphanRemoval = true)
    private List<CartDetailEntity> cartDetails;


    @ManyToMany(targetEntity = TopicEntity.class,fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "painting_topic",
            joinColumns = {
                    @JoinColumn(name = "painting_id",referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "topic_id",referencedColumnName = "id")
            }
    )
    Set<TopicEntity> topics;

    @PostPersist
    protected void onCreate() {
        if (this.code == null) {
            this.code = "PAW" + '-' + getId();
        }
    }

    @PrePersist
    public void prePersist() {
        if (this.inventory == null) {
            this.inventory = 0;
        }if (this.price == null) {
            this.price = 0d;
        }
    }


}
