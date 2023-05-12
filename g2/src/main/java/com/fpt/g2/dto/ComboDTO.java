package com.fpt.g2.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ComboDTO {
    private Long id;
    private String code;
    private String name;
    private String note;
    private Long subjectId;
    private Long curriculumId;
    private List<Long> ids;
}
