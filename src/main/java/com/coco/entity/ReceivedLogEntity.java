package com.coco.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "received_log")
public class ReceivedLogEntity extends BaseEntity{
    private String code;
//    private Date dateAdded;
    private String note;

    @OneToMany(mappedBy = "receivedLog",cascade = {CascadeType.PERSIST, CascadeType.MERGE},orphanRemoval = true)
    private List<DetailReceivedLogEntity> detailReceivedLogs;
    @PostPersist
    protected void onCreate() {
        if (this.code == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String formattedDate = sdf.format(getCreatedDate());
            this.code = "PN_"+ formattedDate + '-'+ getId();
        }
    }

}
