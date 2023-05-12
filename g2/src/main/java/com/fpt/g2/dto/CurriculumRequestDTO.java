package com.fpt.g2.dto;

import com.fpt.g2.entity.Plo;
import lombok.Data;

import java.util.List;

@Data
public class CurriculumRequestDTO {
    private Long id;
    private String code;
    private String name;
    private String englishName;
    private String description;
    private Long totalCredit;
    private String isActive;
    private Long userId;
    private Long decisionId;
    private String decisionNo;
    private List<Plo> Plos;
//    private List<CurriculumSubject> curriculumSubjects;
}