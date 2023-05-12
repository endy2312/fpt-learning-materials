package com.fpt.g2.service;

import com.fpt.g2.dto.PermissionDTO;
import com.fpt.g2.dto.SubHeaderDTO;
import com.fpt.g2.entity.Permission;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PermissionService {
    boolean isRoleCanAccessAll(String role, String endpoint);

    Page getPermissions(PermissionDTO permissionDTO);
    Permission getPermission(String role, String endpoint);

    void updatePermission(Permission permission, Long userId);

    boolean isUserAccessAll(Long userId, String endpoint);

    boolean isUserCanRead(Long userId, String endpoint);

    boolean isUserCanAdd(Long userId, String endpoint);

    boolean isUserCanEdit(Long userId, String endpoint);

    boolean isUserCanDelete(Long userId, String endpoint);

    List<SubHeaderDTO> getListCurriculumHeader(Long userId);

    List<SubHeaderDTO> getListCurriculumHeaderForGuest();

    List<SubHeaderDTO> getListSyllabusHeader(Long userId);

    List<SubHeaderDTO> getListSyllabusHeaderForGuest();
}
