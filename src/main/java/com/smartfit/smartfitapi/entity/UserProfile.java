package com.smartfit.smartfitapi.entity;

import com.smartfit.smartfitapi.model.base.UserProfileBase;
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
public class UserProfile extends UserProfileBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "userProfile")
    UserAccount userAccount;

    @OneToMany(mappedBy = "userProfile")
    List<UserAccess> userAccesses;

    @OneToOne(mappedBy = "userProfile")
    UserOrder userOrder;

    @OneToMany(mappedBy = "userProfile")
    List<UserCourse> userCourses;

    @OneToMany(mappedBy = "userProfile")
    List<UserSession> userSessions;

    @OneToMany(mappedBy = "userProfile")
    List<UserMeal> userMeals;

    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    public UserProfile() {
    }
}
