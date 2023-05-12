package com.fpt.g2.entity;

import com.fpt.g2.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "curriculum_subject")
public class CurriculumSubject extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "curriculum_id")
    private Curriculum curriculum;

    @ManyToOne
    @JoinColumn(name = "subject_group_id")
    private Group subjectGroup;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(name = "semester")
    private Integer semester;

    @Column(name = "no_credit")
    private Integer noCredit;

    @ManyToOne()
    @JoinColumn(name = "syllabus_id")
    private Syllabus syllabus;
}