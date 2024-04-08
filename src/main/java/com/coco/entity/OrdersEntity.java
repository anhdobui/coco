package com.coco.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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
}
