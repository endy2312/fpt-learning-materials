package com.fpt.g2.dto;

import com.fpt.g2.entity.Curriculum;
import com.fpt.g2.entity.Subject;
import com.fpt.g2.entity.Syllabus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SuccessorListDTO {
    private String code;
    private Syllabus syllabus;
    private Curriculum curriculum;
    private Map<String, List<Subject>> subSuccessor;
}
