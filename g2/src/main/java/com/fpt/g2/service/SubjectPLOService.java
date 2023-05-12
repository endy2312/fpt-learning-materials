package com.fpt.g2.service;

import com.fpt.g2.dto.SubjectListPlosDTO;
import com.fpt.g2.dto.SubjectPLOMappingDTO;

import java.util.List;

public interface SubjectPLOService {
    List<SubjectPLOMappingDTO> getListSubjectPLOs(Long curriculumId);

    void updateSubjectPLOMapping(SubjectListPlosDTO request);
}
