package com.fpt.g2.dto;

import com.fpt.g2.entity.Subject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubjectUpdateDTO {
    private Long id;
    private String name;
    private String code;
    private String description;
    private Boolean active;
    private Long parentId;
    private List<Subject> subjects;
    private Boolean isElective;
}
