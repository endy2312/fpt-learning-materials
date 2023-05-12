package com.fpt.g2.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElectiveDTO {
    private Long id;
    private String code;
    private String name;
    private String note;
    private Long subjectId;
    private Long curriculumId;
}
