package com.smartfit.smartfitapi.entity;

import com.smartfit.smartfitapi.model.base.StaffProfileBase;
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
public class StaffProfile extends StaffProfileBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "staffProfile")
    StaffAccount staffAccount;

    @OneToMany(mappedBy = "staffProfile")
    List<StaffAccess> staffAccesses;

    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    public StaffProfile() {
    }
}
