package com.coco.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "account")
public class AccountEntity extends BaseEntity{

    private String fullname;
    private String username;
    private String password;
    private Double grade;
    private String email;
    private String phone;
    private String city;
    private String district;
    private String ward;
    private String pointAddress;

    @OneToMany(mappedBy = "acc",cascade = {CascadeType.PERSIST, CascadeType.MERGE},orphanRemoval = true)
    private List<CartEntity> carts;
}
