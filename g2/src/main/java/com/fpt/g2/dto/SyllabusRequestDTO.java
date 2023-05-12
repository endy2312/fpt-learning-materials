package com.fpt.g2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SyllabusRequestDTO {
    private String name;

    private Integer noCredit;

    private String degreeLevel;

    private String studentTasks;

    private String timeAllocation;

    private String tools;

    private Integer scoringScale;

    private Integer minAvg;

    private String note;

    private String subjectCode;

    private String designer;

    private String reviewer;
}
