package com.fpt.g2.service;

import com.fpt.g2.dto.ElectiveDTO;
import com.fpt.g2.entity.Elective;
import com.fpt.g2.entity.Subject;

import java.util.List;

public interface ElectiveService {
    List<Subject> findByCurriculum(Long curriculumId, Boolean status);


    List<Subject> getAllSubjectChildren(Long subjectId, Long curriculumId);

    List<Subject> getAllExistedElective( List<Subject> electives);

    List<Subject> getSubjectNotParent(List<Subject> childrenElective, Long curriculumId);
}
