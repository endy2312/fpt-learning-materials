package com.fpt.g2.entity;

import com.fpt.g2.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "settings")
public class Setting extends BaseEntity {

    @Column(name = "type")
    private String type;
    @Column(name = "title")
    private String title;
    @Column(name = "value")
    private String value;
    @Column(name = "display_order")
    private Integer displayOrder;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private List<UserRole> roles;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private List<Permission> rolePermissions;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "page_id")
    private List<Permission> pagePermissions;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "degree_level")
    private List<Syllabus> syllabi;
}
