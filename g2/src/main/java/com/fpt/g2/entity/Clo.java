package com.fpt.g2.entity;

import com.fpt.g2.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Table(name = "clo")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Clo extends BaseEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "details")
    private String details;

    @ManyToOne
    @JoinColumn(name = "syllabus_id")
    private Syllabus syllabus;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "clo_id")
    private List<CloPlo> cloPlos;
}
