package com.fpt.g2.entity;

import com.fpt.g2.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Table(name = "syllabus")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Syllabus extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "no_credit")
    private Integer noCredit;

    @ManyToOne
    @JoinColumn(name = "degree_level")
    private Setting degreeLevel;

    @Column(name = "student_tasks")
    private String studentTasks;

    @Column(name = "time_allocation")
    private String timeAllocation;

    @Column(name = "tools")
    private String tools;

    @Column(name = "scoring_scale")
    private Integer scoringScale;

    @Column(name = "min_avg")
    private Integer minAvg;

    @Column(name = "approved_date")
    private Timestamp approvedDate;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "decision_id")
    private Decision decision;

    @ManyToOne
    @JoinColumn(name = "designer_id")
    private User designer;

    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @OneToMany
    @JoinColumn(name = "syllabus_id")
    private List<CurriculumSubject> curriculumSubjects;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "syllabus_id")
    private List<Clo> clos;

    public String getCustomFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(approvedDate);
    }
}
