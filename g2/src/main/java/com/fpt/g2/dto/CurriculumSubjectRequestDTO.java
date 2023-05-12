package com.fpt.g2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurriculumSubjectRequestDTO {
    private Long csId;
    private Integer semester;
    private Integer noCredit;
    private String status;
    private String group;
    private List<Long> subjectIds;
}
