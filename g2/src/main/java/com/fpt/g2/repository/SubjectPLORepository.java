package com.fpt.g2.repository;

import com.fpt.g2.entity.Subject;
import com.fpt.g2.entity.SubjectPLO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectPLORepository extends JpaRepository<SubjectPLO, Long> {

    @Query("select sp from SubjectPLO sp where sp.deleteFlag = false and sp.curriculum.id = ?1")
    List<SubjectPLO> getByCurriculumId(Long curriculumId);

    @Query("select sp from SubjectPLO sp where sp.deleteFlag = false and sp.curriculum.id = ?1 and sp.subject.id = ?2")
    List<SubjectPLO> getAllBySubjectAndCurriculum(Long curriculumId, Long subjectId);

    @Query("select distinct sp.subject from SubjectPLO sp where sp.curriculum.id = ?1")
    List<Subject> getSubjectInSubjectPLO(Long curriculumId);

    @Query("select sp from SubjectPLO sp where sp.subject.code = ?1 and sp.curriculum.id = ?2")
    List<SubjectPLO> getAllBySubjectCodeAndCurriculumId(String code, Long id);
}
