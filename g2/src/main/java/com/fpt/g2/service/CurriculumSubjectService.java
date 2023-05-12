package com.fpt.g2.service;

import com.fpt.g2.dto.CSubjectRequestDTO;
import com.fpt.g2.dto.CSubjectResponseDTO;
import com.fpt.g2.dto.CurriculumSubjectDTO;
import com.fpt.g2.dto.CurriculumSubjectRequestDTO;
import com.fpt.g2.entity.Group;
import com.fpt.g2.entity.Subject;

import java.util.List;

public interface CurriculumSubjectService {
    List<CSubjectResponseDTO> getCurriculumSubjects(Boolean status, Long curriculumId, CSubjectRequestDTO dto);
    Long changeCurriculumSubjectStatus(Long userId, Long id);
    List<Subject> getAllElective(Long curriculumId);
    List<Subject> getElectiveForSelect(Long curriculumId, List<Subject> subjects);
    void addCurriculumSubject(Long subjectId, Long curriculumId);
    List<Subject> getAllSubjectByCurriculum(Long curriculumId);
    void removeGroupSubject(Long subjectId, Long curriculumId);
    List<Subject> getSubjectGroupNotIn(List<Subject> ids,Long curriculumId);

    void addSubjectForGroup(List<Long> subjectIds,Long curriculumId,Long groupId);

    List<Subject> getAllCombo(Long curriculumId);

    List<Group> getGroupsByCurriculum(Long curriculumId);

    Long updateCurriculumSubjectDetails(CurriculumSubjectRequestDTO request);

    void addNewCurriculumSubject(CurriculumSubjectDTO request);
}
