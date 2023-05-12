package com.fpt.g2.utils.plo;

import com.fpt.g2.dto.CurriculumPloDTO;
import com.fpt.g2.entity.Plo;

public class PLOUtils {
    public static CurriculumPloDTO convertToCurriculumPOLDTO(Plo plo, Long curriculumId) {
        CurriculumPloDTO curriculumPloDTO = new CurriculumPloDTO();
        curriculumPloDTO.setCurriculumId(curriculumId);
        curriculumPloDTO.setId(plo.getId());
        curriculumPloDTO.setName(plo.getName());
        curriculumPloDTO.setDescription(plo.getDescription());
        curriculumPloDTO.setIsActive(plo.isDeleteFlag() ? "activate" : "deactivate");

        return curriculumPloDTO;
    }
}
