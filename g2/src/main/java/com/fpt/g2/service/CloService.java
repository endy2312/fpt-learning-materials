package com.fpt.g2.service;

import com.fpt.g2.dto.CLORequestDTO;
import com.fpt.g2.dto.RequestCommonDTO;
import com.fpt.g2.entity.Clo;

import java.util.List;

public interface CloService {
    List<Clo> getAllBySyllabus(Long syllabusId, RequestCommonDTO request);

    Long deleteCLO(Long id);

    Long updateCLO(Clo clo);

    void addNewCLO(CLORequestDTO request);

    Boolean isCLOExisted(Long id, String code);

    List<Clo> getClosBySyllabus(Long syllabusId);
}
