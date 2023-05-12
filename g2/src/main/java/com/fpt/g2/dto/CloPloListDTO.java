package com.fpt.g2.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CloPloListDTO {
    private Long syllabusId;
    private List<CloPloDto> cloPloList;
}
