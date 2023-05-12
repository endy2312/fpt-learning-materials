package com.fpt.g2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubjectPloDTO {
    private Long subjectId;

    private String subjectCode;

    private List<String> PLOName;

    private List<Object> plos;
}
