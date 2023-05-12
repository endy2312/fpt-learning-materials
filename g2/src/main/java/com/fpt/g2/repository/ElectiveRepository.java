package com.fpt.g2.repository;

import com.fpt.g2.entity.Elective;
import com.fpt.g2.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElectiveRepository extends JpaRepository<Elective,Long> {
    @Query(value = "select s.* from curriculum_subject cs " +
            "join subject s on cs.subject_id = s.id " +
            "where cs.curriculum_id = ?1 and s.is_elective = true and (?2 is null or e.delete_flag = ?2)",nativeQuery = true)
    List<Subject> getElectiveByCurriculum(Long curriculumId, Boolean status);

    List<Elective> findAllByIdIsNotInAndDeleteFlagFalse(List<Long> ids);
}
