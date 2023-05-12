package com.fpt.g2.repository;

import com.fpt.g2.entity.PoPlo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PoPloRepository extends JpaRepository<PoPlo, Long> {
    @Query("select p from PoPlo p where p.po.id = ?1 and p.plo.id = ?2")
    PoPlo findByPoIdAndPloId(Long poId, Long ploId);

    @Query("select p from PoPlo p where p.plo.name = ?1 and p.plo.curriculum.id = ?2")
    List<PoPlo> findByPloName(String name, Long cId);

}