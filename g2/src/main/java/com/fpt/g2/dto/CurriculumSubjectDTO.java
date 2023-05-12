package com.fpt.g2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurriculumSubjectDTO {
    private Long curriculumId;
    private String subjectCode;
    private String groupCode;
    private Integer semester;
    private Integer noCredit;
}
