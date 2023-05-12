package com.fpt.g2.repository;

import com.fpt.g2.entity.Decision;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DecisionRepository extends JpaRepository<Decision, Long> {
    @Query("select d from Decision d where d.deleteFlag = false")
    List<Decision> findAll();

    @Query("select d from Decision d where d.deleteFlag  = false and d.decisionNo like :keyword or d.decisionName like :keyword")
    Page<Decision> getAllDecision(Pageable pageable,@Param("keyword") String keyword);

    boolean existsDecisionByDecisionNo(String decisionNo);
}