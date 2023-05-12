package com.fpt.g2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO extends RequestCommonDTO{
    private Long id;
    private String name;
    private String code;
    private String description;
    private Boolean active;
    private Long parentId;
}
