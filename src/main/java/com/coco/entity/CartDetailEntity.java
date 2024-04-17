package com.coco.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cart_detail")
public class CartDetailEntity extends BaseEntity{

    private Integer qty;

    @ManyToOne
    @JoinColumn(name = "painting_id")
    private PaintingEntity painting;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartEntity cart;

    @PostPersist
    protected void onCreate() {
        if (this.qty == null) {
            this.qty = 1;
        }
    }
}
