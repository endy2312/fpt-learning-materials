package com.fpt.g2.dto;

import com.fpt.g2.entity.Curriculum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CloPloResponseDTO {
    private Long syllabusId;
    private Curriculum curriculum;
    private List<PloCloDTO> ploClos;
}
