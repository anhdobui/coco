package com.coco.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cart")
public class CartEntity  extends BaseEntity{
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "acc_id")
    private AccountEntity acc;

    @OneToMany(mappedBy = "cart",cascade = {CascadeType.PERSIST, CascadeType.MERGE},orphanRemoval = true)
    private List<CartDetailEntity> cartDetails;

    @OneToOne(mappedBy = "cart")
    private OrdersEntity orders;
}
