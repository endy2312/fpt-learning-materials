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

@Table(name = "clo_plo")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CloPlo extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "clo_id")
    private Clo clo;

    @ManyToOne
    @JoinColumn(name = "plo_id")
    private Plo plo;
}
