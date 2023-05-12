package com.fpt.g2.repository;

import com.fpt.g2.entity.Curriculum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {

    @Query("select c from Curriculum c where (:keyword is null or c.code like :keyword or c.name like :keyword) and (:status is null or c.isActive = :status)")
    Page findAll(@Param("keyword") String keyword, @Param("status") Boolean status, Pageable pageable);

    @Query("select c from Curriculum c where c.user.id = :userId and (:keyword is null or c.code like :keyword or c.name like :keyword) and (:status is null or c.isActive = :status)")
    Page findAllByStaffId(@Param("keyword") String keyword, @Param("userId") Long userId, @Param("status") Boolean status, Pageable pageable);

    Curriculum findByIdAndDeleteFlagIsFalse(Long id);

    @Query("select c from Curriculum c where c.deleteFlag = false and c.id = ?1")
    Curriculum findCurriculumById(Long id);

    @Query("select c from Curriculum c where c.deleteFlag = false and c.code = ?1")
    Curriculum findCurriculumByCode(String code);

    @Query("select c from Curriculum c where c.deleteFlag = false and c.decision.id = ?1")
    List<Curriculum> findAllCurriculumsByDecisionId(Long dId);
}