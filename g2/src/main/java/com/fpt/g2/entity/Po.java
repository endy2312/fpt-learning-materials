package com.fpt.g2.entity;

import com.fpt.g2.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "po")
public class Po extends BaseEntity {
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "curriculum_id")
    private Curriculum curriculum;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "po_id")
    private List<PoPlo> poPloList;
}