package com.fpt.g2.utils.po;

import com.fpt.g2.dto.CurriculumPoRequestDTO;
import com.fpt.g2.entity.Po;

public class POUtils {
    public static CurriculumPoRequestDTO convertToCurriculumPoRequestDTO(Po po, Long curriculumId) {
        CurriculumPoRequestDTO curriculumPoRequestDTO = new CurriculumPoRequestDTO();
        curriculumPoRequestDTO.setCurriculumId(curriculumId);
        curriculumPoRequestDTO.setId(po.getId());
        curriculumPoRequestDTO.setName(po.getName());
        curriculumPoRequestDTO.setDescription(po.getDescription());
        curriculumPoRequestDTO.setIsActive(po.isDeleteFlag() ? "activate" : "deactivate");

        return curriculumPoRequestDTO;
    }
}
