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
@Table(name = "po_plo")
public class PoPlo extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "plo_id")
    private Plo plo;

    @ManyToOne
    @JoinColumn(name = "po_id")
    private Po po;
}