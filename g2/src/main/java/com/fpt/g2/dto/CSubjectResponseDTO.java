package com.fpt.g2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CSubjectResponseDTO {

    private Long id;

    private String code;

    private Long subjectId;

    private String name;

    private Integer semester;

    private Integer noCredit;

    private Boolean isActive;

    private String predecessor;

    private Timestamp createdDate;

    private Timestamp updatedDate;

    private boolean deleteFlag;

    private String group;
}
