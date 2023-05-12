package com.fpt.g2.service;

import com.fpt.g2.dto.PredecessorListDTO;
import com.fpt.g2.dto.SuccessorListDTO;
import com.fpt.g2.entity.Curriculum;
import com.fpt.g2.entity.Subject;

import java.util.List;

import java.util.List;

public interface PreRequisiteService {
    List<PredecessorListDTO> getPredecessor(String code);

    List<SuccessorListDTO> getSuccessor(String code);

    String getPreByCurriculumSubject(Long curriculumId, Long subjectId);

    String getPreBySubject(Long subjectId);

    void addNewPredecessor(Curriculum curriculum, Subject subject, List<Long> subjectIds);
}
