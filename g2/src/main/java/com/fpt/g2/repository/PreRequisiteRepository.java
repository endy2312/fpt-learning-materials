package com.fpt.g2.repository;

import com.fpt.g2.entity.Curriculum;
import com.fpt.g2.entity.PreRequisite;
import com.fpt.g2.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreRequisiteRepository extends JpaRepository<PreRequisite,Long> {
    @Query("select p.predecessor from PreRequisite p WHERE p.successor.code = :code and p.curriculum.id = :id AND p.deleteFlag = false")
    List<Subject> findPredecessorByCode(@Param("code")String code, @Param("id") Long id);

    @Query("select p.successor from PreRequisite p WHERE p.predecessor.code = :code and p.curriculum.id = :id AND p.deleteFlag = false")
    List<Subject> findSuccessorByCode(@Param("code")String code, @Param("id") Long id);

    @Query("select distinct p.curriculum from PreRequisite p where p.successor.code = ?1")
    List<Curriculum> getCurriculumInPredecessor(String code);

    @Query("select distinct p.curriculum from PreRequisite p where p.predecessor.code = ?1")
    List<Curriculum> getCurriculumInSuccessor(String code);

    @Query("select p.successor from PreRequisite p WHERE p.predecessor.code = :code AND p.deleteFlag = false")
    Page findSuccessorByCode(@Param("code")String code, Pageable pageable);

    @Query("select p from PreRequisite p where p.deleteFlag = false and p.curriculum.id = ?1 and p.successor.id = ?2")
    List<PreRequisite> getListPredecessor(Long curriculumId, Long subjectId);

    @Query("select p from PreRequisite p where p.deleteFlag = false and p.successor.id = ?1")
    List<PreRequisite> getListPredecessorBySubject(Long subjectId);

    @Query("select (count(p) > 0) from PreRequisite p where p.curriculum.id = ?1 and p.successor.id = ?2 and p.predecessor.id = ?3")
    boolean isPredecessorsExisted(Long curriculumId, Long successorId, Long predecessorId);
}
