package com.fpt.g2.entity;

import com.fpt.g2.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Table(name = "subject")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subject extends BaseEntity {
    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "is_elective")
    private Boolean isElective;

    @Column(name = "is_combo")
    private Boolean isCombo;

    @OneToMany
    @JoinColumn(name = "predecessor_id")
    private Set<PreRequisite> predecessors;

    @OneToMany
    @JoinColumn(name = "successor_id")
    private Set<PreRequisite> successors;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private List<CurriculumSubject> curriculumSubjects;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private List<SubjectPLO> subjectPLOS;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id")
    private List<Syllabus> syllabi;
}
