package com.fpt.g2.repository;

import com.fpt.g2.entity.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query("select (count(p) > 0) from Permission p where p.deleteFlag = false and p.accessAll = true" +
            " and p.role.title = ?1 and p.page.value = ?2")
    boolean isRoleCanAccessAll(String role, String endpoint);

    @Query("select p from Permission p where p.deleteFlag = false" +
            " and (:role is null or p.role.title = :role) and (:title is null or p.page.title LIKE :title)")
    Page getPermissions(@Param("role") String role, @Param("title") String title, Pageable pageable);

    @Query("select (count(p) > 0) from Permission p where p.deleteFlag = false and p.canRead = true" +
            " and p.role.title = ?1 and p.role.deleteFlag = false and p.page.value = ?2")
    boolean isRoleCanRead(String role, String endpoint);

    @Query("select (count(p) > 0) from Permission p where p.deleteFlag = false and p.canAdd = true" +
            " and p.role.title = ?1 and p.role.deleteFlag = false and p.page.value = ?2")
    boolean isRoleCanAdd(String role, String endpoint);

    @Query("select (count(p) > 0) from Permission p where p.deleteFlag = false and p.canEdit = true" +
            " and p.role.title = ?1 and p.role.deleteFlag = false and p.page.value = ?2")
    boolean isRoleCanEdit(String role, String endpoint);

    @Query("select (count(p) > 0) from Permission p where p.deleteFlag = false and p.canDelete = true" +
            " and p.role.title = ?1 and p.role.deleteFlag = false and p.page.value = ?2")
    boolean isRoleCanDelete(String role, String endpoint);

    @Query("select p from Permission p where p.deleteFlag = false and p.id = ?1")
    Permission findPermission(Long id);

    @Query("select p from Permission p where p.deleteFlag = false and p.role.title = ?1 and p.page.value = ?2")
    Permission getPermissionByRoleAndPage(String role, String endpoint);
}
