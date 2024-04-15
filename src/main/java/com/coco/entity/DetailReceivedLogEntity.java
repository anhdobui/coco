package com.coco.entity;

import jakarta.persistence.*;
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

    @PostPersist
    protected void sumInventory(){
        if(this.qty != null && this.qty > 0){
            this.painting.setInventory(this.painting.getInventory() + this.qty);
        }
    }

}
