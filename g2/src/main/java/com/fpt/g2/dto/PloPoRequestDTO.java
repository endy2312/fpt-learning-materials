package com.fpt.g2.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PloPoRequestDTO {
    private Long curriculumId;
    private List<PloPoDTO> plopos;
}
