package com.fpt.g2.repository;

import com.fpt.g2.entity.Setting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettingRepository extends JpaRepository<Setting,Long> {
    Setting findByTypeAndTitle(String title,String type);

    @Query("select s from Setting s where s.type = 'User Role' and s.deleteFlag = false")
    List<Setting> getRoles();

    @Query("select s from Setting s where (:isActive is null or s.deleteFlag = :isActive)" +
            " and (:type is null or s.type = :type) and (:title is null or s.title LIKE :title)")
    Page getSettings(@Param("type") String type, @Param("title") String title, @Param("isActive") Boolean isActive, Pageable pageable);

    @Query("select distinct s.type from Setting s")
    List<String> getTypes();

    @Query("select s from Setting s where s.id = ?1")
    Setting getById(Long id);

    @Query("select s from Setting s where s.type = 'Page' and s.value = ?1")
    Setting getPageByEndpoint(String endpoint);

    @Query("select s from Setting s where s.deleteFlag = false and s.type = 'Degree Level'")
    List<Setting> getDegreeLevels();
}
