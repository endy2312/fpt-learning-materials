package com.fpt.g2.repository;

import com.fpt.g2.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
    @Query("select ur from UserRole ur where ur.deleteFlag = false and ur.isActive = true and ur.user.id = ?1")
    List<UserRole> getRolesByUserId(Long id);

    @Query("select ur from UserRole ur where ur.deleteFlag = false and ur.user.id = ?1")
    List<UserRole> getAllRolesByUserId(Long id);

    @Query("select ur from UserRole ur where ur.deleteFlag = false and ur.setting.id = ?1 and ur.user.id = ?2")
    UserRole getRoleByUserIdAndSettingId(Long settingId, Long userId);

    @Query("select ur from UserRole ur where ur.deleteFlag = false and ur.isActive = true and ur.setting.title = ?1")
    List<UserRole> getAllRolesByRoleName(String roleName);

}
