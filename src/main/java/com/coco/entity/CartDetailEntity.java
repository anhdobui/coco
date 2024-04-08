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
@Table(name = "cart_detail")
public class CartDetailEntity extends BaseEntity{

    private Integer qty;

    @ManyToOne
    @JoinColumn(name = "painting_id")
    private PaintingEntity painting;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartEntity cart;
}
