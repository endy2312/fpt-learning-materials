package com.fpt.g2.repository;

import com.fpt.g2.entity.Clo;
import com.fpt.g2.entity.CloPlo;
import com.fpt.g2.entity.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CloPloRepository extends JpaRepository<CloPlo, Long> {

    @Query("select distinct cpp.curriculum from CloPlo cp join cp.plo cpp join cp.clo cpc where cpc.syllabus.id = ?1")
    List<Curriculum> getCurriculumInCloPloMapping(Long syllabusId);

    @Query("select cp.clo from CloPlo cp join cp.clo cpc where cpc.syllabus.id = ?1 and cp.plo.id = ?2")
    List<Clo> getClosByPloAndSyllabus(Long syllabusId, Long ploId);

    @Query("select cp from CloPlo cp join cp.clo cpc where cpc.syllabus.id = ?1 and cp.plo.id = ?2")
    List<CloPlo> getMappingByPloAndSyllabus(Long syllabusId, Long ploId);
}
