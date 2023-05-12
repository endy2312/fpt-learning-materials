package com.fpt.g2.entity;

import com.fpt.g2.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "content_group")
public class Group extends BaseEntity {
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_group_id")
    private List<CurriculumSubject> curriculumSubjects;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "curriculum_id")
    private Long curriculumId;
}
