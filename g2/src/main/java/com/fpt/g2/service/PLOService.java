package com.fpt.g2.service;

import com.fpt.g2.dto.CurriculumPloDTO;
import com.fpt.g2.entity.Plo;

import java.util.List;

public interface PLOService {

    List<Plo> getPloListByCurriculumId(Long id);

    Plo getPLOByCurriculumIdAndPLOId(Long curriculumId, Long ploId);

    String saveOrUpdate(CurriculumPloDTO curriculumPlo);

    void deleteCurriculumPLO(Long curriculumId, Long ploId);
}
