package com.fpt.g2.entity;

import com.fpt.g2.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "plo_subject")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectPLO extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "plo_id")
    private Plo plo;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "curriculum_id")
    private Curriculum curriculum;
}
