package com.fpt.g2.repository;

import com.fpt.g2.entity.Plo;
import com.fpt.g2.entity.Po;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PoRepository extends JpaRepository<Po, Long> {

    @Query("select po from Po po where po.curriculum.id = ?1")
    List<Po> findAllByCurriculumId(Long id);

    @Query("select po from Po po where po.curriculum.id = ?1 and lower(po.name) = lower(?2)")
    Po existsPOByName(Long cId, String name);

    @Query("select po from Po po where po.curriculum.id = ?1 and po.id = ?2")
    Po findByCurriculumIdAndPOId(Long cId, Long ploId);

    @Query("select po from Po po where po.curriculum.id = ?1 and po.name = ?2")
    Po findByCurriculumIdAndPOName(Long cId, String po);

}