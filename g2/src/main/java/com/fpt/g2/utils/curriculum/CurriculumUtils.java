package com.fpt.g2.utils.curriculum;

import com.fpt.g2.dto.CurriculumRequestDTO;
import com.fpt.g2.entity.Curriculum;

public class CurriculumUtils {
    public static Curriculum convertToCurriculum(CurriculumRequestDTO curriculumDto) {
        Curriculum curriculum = new Curriculum();
        curriculum.setId(curriculumDto.getId());
        curriculum.setCode(curriculumDto.getCode());
        curriculum.setName(curriculumDto.getName());
        curriculum.setEnglishName(curriculumDto.getEnglishName());
        curriculum.setDescription(curriculumDto.getDescription());
        curriculum.setTotalCredit(curriculumDto.getTotalCredit());
        curriculum.setActive(curriculumDto.getIsActive().equalsIgnoreCase("activate") ? true : false);
        return curriculum;
    }

    public static CurriculumRequestDTO convertToCurriculumDTO(Curriculum curriculum) {
        CurriculumRequestDTO curriculumDto = new CurriculumRequestDTO();
        curriculumDto.setId(curriculum.getId());
        curriculumDto.setCode(curriculum.getCode());
        curriculumDto.setName(curriculum.getName());
        curriculumDto.setEnglishName(curriculum.getEnglishName());
        curriculumDto.setDescription(curriculum.getDescription());
        curriculumDto.setTotalCredit(curriculum.getTotalCredit());
        curriculumDto.setIsActive(curriculum.isActive() ? "activate" : "deactivate");
        curriculumDto.setDecisionId(curriculum.getDecision() != null ? curriculum.getDecision().getId() : null);
        curriculumDto.setDecisionNo(curriculum.getDecision() != null ? curriculum.getDecision().getCustomFormat() : "");
        curriculumDto.setUserId(curriculum.getUser() != null ? curriculum.getUser().getId() : null);
        return curriculumDto;
    }

}
