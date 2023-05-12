package com.fpt.g2.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CurriculumPoRequestDTO {
    private Long curriculumId;
    private Long id;
    private String name;
    private String description;
    private String isActive;
}
