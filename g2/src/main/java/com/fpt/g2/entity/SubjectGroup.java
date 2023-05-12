package com.fpt.g2.entity;

import com.fpt.g2.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "subject_group")
public class SubjectGroup extends BaseEntity {
    private String groupType;
    private String name;

    @ManyToOne
    @JoinColumn(name = "curriculum_id")
    private Curriculum curriculum;

//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name = "subject_group_id")
//    private List<CurriculumSubject> curriculumSubjects;
}