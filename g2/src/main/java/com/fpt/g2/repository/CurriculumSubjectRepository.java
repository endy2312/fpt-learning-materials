package com.fpt.g2.repository;

import com.fpt.g2.entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurriculumSubjectRepository extends JpaRepository<CurriculumSubject, Long> {

    @Query("select cs from CurriculumSubject cs where cs.curriculum.id = :id and (:status is null or cs.deleteFlag = :status)")
    List<CurriculumSubject> getCurriculumSubjects(@Param("id") Long curriculumId, @Param("status") Boolean status, Pageable pageable);

    @Query("select cs from CurriculumSubject cs where cs.id = ?1")
    CurriculumSubject getById(Long id);

    @Query("select distinct cs.subjectGroup from CurriculumSubject cs where cs.curriculum.id = ?1 and cs.subjectGroup.deleteFlag = false")
    List<Group> getGroupsByCurriculum(Long curriculumId);

    @Query("select (count (cs) > 0) from CurriculumSubject cs where cs.curriculum.id = ?1 and cs.subject.id = ?2 and cs.subjectGroup.id = ?3")
    boolean isSubjectInsideGroup(Long curriculumId, Long subjectId, Long groupId);

    @Query("select cs.subject from CurriculumSubject cs where cs.subject.isElective = true and cs.curriculum.id = :curriculumId")
    List<Subject> getAllElectiveSubject(@Param("curriculumId") Long curriculumId);

    @Query("select cs.subject from CurriculumSubject cs where cs.curriculum.id = ?1 and cs.subject.id not in ?2")
    List<Subject> getSubjectNotInIds(Long curriculumId, List<Long> ids);

    @Query("select cs.subject from CurriculumSubject cs where cs.curriculum.id = ?1 and cs.subject.deleteFlag = false")
    List<Subject> findAllByCurriculum(Long curriculumId);

    @Query("select cs from CurriculumSubject cs where cs.curriculum.id = :id and cs.subject.id in :ids")
    List<CurriculumSubject> getAllByCurriculumAndSubjectIds(@Param("id") Long curriculum,@Param("ids") List<Long> ids);

    @Query("select cs.subject from CurriculumSubject cs where cs.subjectGroup.id = ?1 and cs.curriculum.id = ?2 and cs.subject.deleteFlag = false")
    List<Subject> getAllByGroupAndCurriculum(Long groupId,Long curriculumId);

    @Query("select cs from CurriculumSubject cs where cs.subject.id = ?1 and cs.curriculum.id = ?2 and cs.deleteFlag = false")
    CurriculumSubject getBySubjectAndCurriculum(Long subjectId,Long curriculumId);

    @Query("select cs.subject from CurriculumSubject cs where (coalesce(?1,null) is null  or cs.subject.id not in ?1) and cs.curriculum.id = ?2")
    List<Subject> getSubjectGroupNotIn(List<Long> ids, Long curriculumId);

    @Query("select cs from CurriculumSubject cs where cs.subjectGroup.id = ?1")
    List<CurriculumSubject> findByGroupId(Long id);

    @Query("select cs.subject from CurriculumSubject cs where cs.subject.isCombo = true and cs.curriculum.id = :curriculumId")
    List<Subject> getAllComboSubject(@Param("curriculumId") Long curriculumId);

    @Query("select cs.curriculum from CurriculumSubject cs where cs.syllabus.id = ?1")
    List<Curriculum> getCurriculumsBySyllabus(Long syllabusId);

    @Query("select cs.syllabus from CurriculumSubject cs where cs.subject.code = ?1 and cs.curriculum.id = ?2 and cs.deleteFlag = false")
    Syllabus findCurriculumAndSyllabus(String code, Long id);
}