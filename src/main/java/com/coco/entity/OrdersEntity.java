package com.coco.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class OrdersEntity extends BaseEntity{

    private String code;
    private Integer status;
    private Date orderDate;
    private Date deliveryDate;
    private Date cancellationDate;
    private Date finishedDate;

    @OneToOne
    @JoinColumn(name = "cart_id")
    private CartEntity cart;

    @PrePersist
    public void prePersist() {
        this.orderDate = new Date();
        this.status = 1;
    }

    @PostPersist
    protected void onCreate() {
        if (this.code == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String formattedDate = sdf.format(this.orderDate);
            this.code = "ORD-"+formattedDate+"-"+ getId();
        }
        if(this.cart != null){
            this.cart.setStatus(0);
        }
    }
    @PreUpdate
    protected void onUpdate() {
        if (this.status != null) {
            if (this.status == 2 && this.deliveryDate == null) {
                // If status is 2 (confirmed) and deliveryDate is not set, set deliveryDate to current date and time
                this.deliveryDate = new Date();
            } else if (this.status == 3 && this.finishedDate == null) {
                // If status is 3 (completed) and finishedDate is not set, set finishedDate to current date and time
                this.finishedDate = new Date();
            } else if (this.status == 0 && this.cancellationDate == null) {
                // If status is 0 (cancelled) and cancellationDate is not set, set cancellationDate to current date and time
                this.cancellationDate = new Date();
            }
        }
    }
}
