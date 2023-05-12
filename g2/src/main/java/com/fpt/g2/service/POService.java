package com.fpt.g2.service;

import com.fpt.g2.dto.CurriculumPloDTO;
import com.fpt.g2.dto.CurriculumPoRequestDTO;
import com.fpt.g2.entity.Po;

import java.util.List;

public interface POService {

    List<Po> getPoListByCurriculumId(Long id);

    Po getPoByCurriculumIdAndPOId(Long curriculumId, Long poId);

    String saveOrUpdate(CurriculumPoRequestDTO curriculumPo);

    void deleteCurriculumPO(Long curriculumId, Long ploId);
}
