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
@Table(name = "pre_requisite")
public class PreRequisite extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "predecessor_id")
    private Subject predecessor;

    @ManyToOne
    @JoinColumn(name = "successor_id")
    private Subject successor;

    @ManyToOne
    @JoinColumn(name = "curriculum_id")
    private Curriculum curriculum;
}
