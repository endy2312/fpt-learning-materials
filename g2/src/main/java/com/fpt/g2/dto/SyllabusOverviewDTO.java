package com.fpt.g2.dto;

import com.fpt.g2.entity.Syllabus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SyllabusOverviewDTO {
    private Syllabus syllabus;
    private String predecessors;
}
