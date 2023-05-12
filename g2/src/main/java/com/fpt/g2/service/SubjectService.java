package com.fpt.g2.service;


import com.fpt.g2.dto.*;
import com.fpt.g2.entity.Subject;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SubjectService {

   Page getAllSubject(SubjectDTO dto);

   void addOrUpdate(SubjectDTO subjectDTO,Long useId);

   Boolean isSubjectExisted(String subject,Long id,String type);

    void updateActive(SubjectListDTO subjectList,Long userId);

   SubjectUpdateDTO initUpdateSubject(Long id);

   List<Subject> findElectiveChildrens(Long electiveParentId);

   void addElectiveSubject(Long parentId, List<Long> subjectIds);

   void removeParentSubject(Long childId);

   List<Subject> getAllSubjects();

    Subject findById(Long parentId);

    void addElective(ElectiveDTO elective);

    boolean checkCodeExisted(String code, Long id, String add);

    void updateCombo(ElectiveDTO elective);

    List<Subject> findByCurriculum(Long curriculumId, Boolean status);

    List<Subject> getAllExistedCombo(List<Subject> combos);

    List<Subject> getAllSubjectChildrenCombo(Long parentId, Long curriculumId);

    void addCombo(ComboDTO combo);

    List<Subject> getSubjectNotParent(List<Subject> childrenSubject, Long curriculumId);
    void updateCombo(ComboDTO combo);

   List<Subject> getSubjectsOutsideCurriculum(Long curriculumId);
}
