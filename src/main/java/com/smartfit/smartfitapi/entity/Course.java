package com.smartfit.smartfitapi.entity;

import com.smartfit.smartfitapi.model.base.CourseBase;
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
public class Course extends CourseBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "course")
    List<CourseStep> courseSteps;

    @OneToMany(mappedBy = "course")
    List<UserCourse> userCourses;

    @OneToMany(mappedBy = "course")
    List<UserSession> userSessions;

    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    public Course() {
    }
}
