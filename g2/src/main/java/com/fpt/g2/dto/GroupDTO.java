package com.fpt.g2.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupDTO {
    private Long id;
    private String code;
    private String name;
    private List<Long> subjectIds;
    private Long curriculumId;
}
