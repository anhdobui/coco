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



    @OneToMany(mappedBy = "painting",cascade = {CascadeType.PERSIST, CascadeType.MERGE},orphanRemoval = true)
    private List<DetailReceivedLogEntity> detailReceivedLogs;

    @OneToMany(mappedBy = "painting",cascade = {CascadeType.PERSIST, CascadeType.MERGE},orphanRemoval = true)
    private List<CartDetailEntity> cartDetails;


    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
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

}
