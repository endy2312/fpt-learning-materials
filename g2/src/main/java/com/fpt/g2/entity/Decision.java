package com.fpt.g2.entity;

import com.fpt.g2.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "decision")
public class Decision extends BaseEntity {
    private String decisionNo;
    private Date decisionDate;
    private String decisionName;
    private String note;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "decision_id")
    private List<Curriculum> curriculums;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "decision_id")
    private List<Syllabus> syllabi;

    public String getCustomFormat() {
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return decisionNo + " dated " + sdf.format(decisionDate);
    }

    public String getCustomFormatDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return decisionDate != null ? sdf.format(decisionDate) : "";
    }
}