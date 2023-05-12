package com.fpt.g2.repository;

import com.fpt.g2.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("select g from Group g where g.id <> ?1 and g.code = ?2")
    List<Group> findAllByIdIsNotInAndCodeLike(Long id,String code);

    List<Group> findAllByCode(String code);

    @Query("select g from Group g where g.curriculumId = ?1 and g.code = ?2")
    Group findByCurriculumAndCode(Long id, String code);
}
