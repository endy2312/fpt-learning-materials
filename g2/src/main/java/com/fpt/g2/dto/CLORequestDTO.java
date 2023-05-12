package com.fpt.g2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CLORequestDTO {
    private Long syllabusId;
    private String cloCode;
    private String cloDetails;
}
