package com.fpt.g2.entity;

import com.fpt.g2.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "combo")
public class Combo extends BaseEntity {
    @Column(name = "code")
    private String code;

    @Column(name = "subject_id")
    private Long subjectId;

    @Column(name = "name")
    private String name;

    @Column(name = "note")
    private String note;
}