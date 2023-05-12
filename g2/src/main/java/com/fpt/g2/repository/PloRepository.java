package com.fpt.g2.repository;

import com.fpt.g2.entity.Plo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PloRepository extends JpaRepository<Plo, Long> {

    @Query("select plo from Plo plo where plo.curriculum.id = ?1")
    List<Plo> findAllByCurriculumId(Long id);

    @Query("select plo from Plo plo where plo.curriculum.id = ?1 and lower(plo.name) = lower(?2)")
    Plo existsPLOByName(Long cId, String name);

    @Query("select plo from Plo plo where plo.curriculum.id = ?1 and plo.id = ?2")
    Plo findByCurriculumIdAndPLOId(Long cId, Long ploId);

    @Query("select p from Plo p where p.name = ?1 and p.curriculum.id = ?2")
    Plo findByPloNameAndCurriculumId(String ploName, Long curriculumId);

    Plo findPloById(Long id);
}