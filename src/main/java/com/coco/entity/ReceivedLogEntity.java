package com.coco.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "received_log")
public class ReceivedLogEntity extends BaseEntity{
    private String code;
    private Date dateAdded;

    @OneToMany(mappedBy = "receivedLog",cascade = {CascadeType.PERSIST, CascadeType.MERGE},orphanRemoval = true)
    private List<DetailReceivedLogEntity> detailReceivedLogs;
    @PrePersist
    protected void onCreate() {
        if (this.code == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String formattedDate = sdf.format(getDateAdded());
            this.code = "PN_"+ formattedDate + '-'+ getId();
        }
    }
}
