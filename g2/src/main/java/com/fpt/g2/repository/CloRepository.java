package com.fpt.g2.repository;

import com.fpt.g2.entity.Clo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CloRepository extends JpaRepository<Clo, Long> {

    @Query("select c from Clo c where c.syllabus.id = ?1")
    List<Clo> getAllBySyllabusId(Long syllabusId, Pageable pageable);

    Clo findCloById(Long id);

    @Query("select (count(c) > 0) from Clo c where c.syllabus.id = ?1 and c.code = ?2")
    boolean isCodeExisted(Long id, String code);

    @Query("select c from Clo c where c.syllabus.id = ?1")
    List<Clo> getClosBySyllabusId(Long syllabusId);
}
