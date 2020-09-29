package com.smartfit.smartfitapi.entity;

import com.smartfit.smartfitapi.model.base.UserOrderBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
public class UserOrder extends UserOrderBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_profile_id")
    UserProfile userProfile;

    @OneToMany(mappedBy = "userOrder")
    List<UserPayment> userPayments;

    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    public UserOrder() {
    }
}
