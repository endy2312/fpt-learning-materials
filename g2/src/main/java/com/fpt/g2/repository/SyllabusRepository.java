package com.fpt.g2.repository;

import com.fpt.g2.entity.Syllabus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SyllabusRepository extends JpaRepository<Syllabus, Long> {

    @Query("select s from Syllabus s where s.id = ?1")
    Syllabus getSyllabusById(Long id);

    @Query("select s from Syllabus s where s.deleteFlag = false and (:status is null or s.status = :status)" +
            " and (:keyword is null or (s.subject.code like :keyword or s.subject.name like :keyword))")
    Page getAll(@Param("status") String status, @Param("keyword") String keyword, Pageable pageable);

    @Query("select distinct s.status from Syllabus s")
    List<String> getAllStatus();

    @Query("select s from Syllabus s where s.decision.id = ?1")
    List<Syllabus> findAllByDecisionId(Long dId);

    @Query("select s from Syllabus s where s.subject.id = ?1 and s.deleteFlag = false")
    List<Syllabus> findAllBySubject(Long subjectId);
}
