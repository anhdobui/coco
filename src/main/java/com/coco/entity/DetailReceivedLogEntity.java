package com.coco.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "detail_received_log")
public class DetailReceivedLogEntity extends BaseEntity{
    private Integer qty;
    private Double unitPrice;

    @ManyToOne
    @JoinColumn(name = "painting_id")
    private PaintingEntity painting;

    @ManyToOne
    @JoinColumn(name = "receivedLog_id")
    private ReceivedLogEntity receivedLog;

}
