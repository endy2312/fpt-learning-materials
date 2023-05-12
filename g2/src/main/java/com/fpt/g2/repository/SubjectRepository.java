package com.fpt.g2.repository;

import com.fpt.g2.entity.Elective;
import com.fpt.g2.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query("SELECT s FROM Subject s WHERE (s.code LIKE :keyword OR s.name LIKE :keyword) AND (:active IS NULL OR s.deleteFlag = :active)")
    Page finAllSubject(@Param("keyword") String keyword, @Param("active") Boolean active, Pageable pageable);

    Subject findSubjectByCodeAndDeleteFlagFalse(String code);

    @Query("select s from Subject s where s.id != ?1 and s.code = ?2")
    Subject findSubjectByIdNot(Long id, String code);

    List<Subject> findAllByIdIsNotAndParentIdIsNull(Long id);

    List<Subject> findAllByParentIdAndDeleteFlagFalse(Long parentId);

    @Query(value = "select s.* from subject s " +
            "join curriculum_subject cs on cs.subject_id = s.id " +
            "where s.parent_id  = ?1 and cs.curriculum_id = 1 and s.delete_flag = false", nativeQuery = true)
    List<Subject> getAllSubjectChildren(Long parentId, Long curriculumId);

    @Query("select s from Subject s where (coalesce(?1,null) is null  or s.id not in ?1) and s.parentId is null " +
            "and s.isCombo = false  and s.isElective = false and s.deleteFlag = false ")
    List<Subject> findAllNotInIds(List<Long> ids);

    @Query("select cs.subject from CurriculumSubject cs where cs.curriculum.id = ?2 and (coalesce(?1,null) is null  " +
            "or cs.subject.id not in ?1) and cs.subject.parentId is null and cs.subject.isCombo = false  " +
            "and cs.subject.isElective = false and cs.deleteFlag = false")
    List<Subject> findAllNotInIds(List<Long> ids, Long curriculumId);

    @Query("select s from Subject s where s.deleteFlag = false")
    List<Subject> getAllSubjects();

    Subject findSubjectByCode(String code);

    @Query(value = "select s.* from curriculum_subject cs " +
            "join subject s on cs.subject_id = s.id " +
            "where cs.curriculum_id = ?1 and s.is_elective = true and (?2 is null or s.delete_flag = ?2)", nativeQuery = true)
    List<Subject> getElectiveByCurriculum(Long curriculumId, Boolean status);

    @Query("select s from Subject s where s.isElective = true and  (coalesce(?1,null) is null  or s.id not in ?1) and s.deleteFlag = false")
    List<Subject> findAllByIdIsNotInAndDeleteFlagFalse(List<Long> ids);

    List<Subject> findAllByIdIsNotAndCodeLike(Long id, String code);

    List<Subject> findAllByCode(String code);

    @Query(value = "select s.* from curriculum_subject cs " +
            "join subject s on cs.subject_id = s.id " +
            "where cs.curriculum_id = ?1 and s.is_combo = true and (?2 is null or s.delete_flag = ?2)", nativeQuery = true)
    List<Subject> getComboByCurriculum(Long curriculumId, Boolean status);

    @Query("select s from Subject s where s.isCombo = true and  (coalesce(?1,null) is null  or s.id not in ?1) and s.deleteFlag = false")
    List<Subject> findExistedCombo(List<Long> ids);

    @Query(value = "select s.* from subject s " +
            "join curriculum_subject cs on cs.subject_id = s.id " +
            "where s.parent_id  = ?1 and cs.curriculum_id = 1 and s.delete_flag = false", nativeQuery = true)
    List<Subject> getAllSubjectChildrenCombo(Long parentId, Long curriculumId);

    @Query(value = "select s2.* from subject s2 where s2.id not in " +
            "(select s.id from subject s join curriculum_subject cs on s.id = cs.subject_id where cs.curriculum_id = ?1)", nativeQuery = true)
    List<Subject> getSubjectsOutsideCurriculum(Long curriculumId);

    Subject findSubjectById(Long id);
}
