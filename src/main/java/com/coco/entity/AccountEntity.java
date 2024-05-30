package com.coco.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "account")
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_account_role"))
    @JsonBackReference
    private Role role;

    @OneToMany(mappedBy = "account")
    @JsonManagedReference
    private List<ConfirmEmail> confirmEmailList;

    @PostPersist
    protected void onCreate() {
        if (this.grade == null) {
            this.grade = 0d;
        }
    }


}
