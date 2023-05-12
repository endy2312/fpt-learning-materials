package com.fpt.g2.entity;

import com.fpt.g2.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "permission")
public class Permission extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Setting role;

    @ManyToOne
    @JoinColumn(name = "page_id")
    private Setting page;

    @Column(name = "access_all")
    private Boolean accessAll;

    @Column(name = "can_read")
    private Boolean canRead;

    @Column(name = "can_add")
    private Boolean canAdd;

    @Column(name = "can_edit")
    private Boolean canEdit;

    @Column(name = "can_delete")
    private Boolean canDelete;
}
