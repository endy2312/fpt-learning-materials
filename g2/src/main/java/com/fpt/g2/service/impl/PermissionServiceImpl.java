package com.fpt.g2.service.impl;

import com.fpt.g2.constant.URLConstant;
import com.fpt.g2.dto.PermissionDTO;
import com.fpt.g2.dto.SubHeaderDTO;
import com.fpt.g2.entity.Permission;
import com.fpt.g2.entity.UserRole;
import com.fpt.g2.repository.PermissionRepository;
import com.fpt.g2.repository.UserRoleRepository;
import com.fpt.g2.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private UserRoleRepository roleRepository;

    @Override
    public boolean isRoleCanAccessAll(String role, String endpoint) {
        if(role != null && !role.isEmpty() && endpoint != null && !endpoint.isEmpty()){
            return permissionRepository.isRoleCanAccessAll(role, endpoint);
        }
        return false;
    }

    @Override
    public Page getPermissions(PermissionDTO permission) {
        Pageable pageable = PageRequest.of(permission.getPage(), permission.getSize(), permission.getSortBy());
        String keyword = permission.getKeyword();
        if(keyword != null  && !permission.getKeyword().isEmpty()){
            keyword = "%" + keyword + "%";
        }
        return permissionRepository.getPermissions(permission.getRole(), keyword, pageable);
    }

    @Override
    public Permission getPermission(String role, String endpoint) {
        return permissionRepository.getPermissionByRoleAndPage(role, endpoint);
    }

    @Override
    public void updatePermission(Permission permission, Long userId) {
        Permission per = permissionRepository.findPermission(permission.getId());
        if(per != null) {
            per.setAccessAll(permission.getAccessAll());
            per.setCanRead(permission.getCanRead());
            per.setCanAdd(permission.getCanAdd());
            per.setCanEdit(permission.getCanEdit());
            per.setCanDelete(permission.getCanDelete());
            per.setUpdatedBy(userId);
            permissionRepository.save(per);
        }
    }

    @Override
    public boolean isUserAccessAll(Long userId, String endpoint) {
        List<UserRole> roles = roleRepository.getRolesByUserId(userId);
        if(roles == null || roles.size() == 0){
            return true;
        }
        for(UserRole role : roles){
            if(permissionRepository.isRoleCanAccessAll(role.getSetting().getTitle(), endpoint)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isUserCanRead(Long userId, String endpoint) {
        List<UserRole> roles = roleRepository.getRolesByUserId(userId);
        for(UserRole role : roles){
            if(permissionRepository.isRoleCanRead(role.getSetting().getTitle(), endpoint)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isUserCanAdd(Long userId, String endpoint) {
        List<UserRole> roles = roleRepository.getRolesByUserId(userId);
        for(UserRole role : roles){
            if(permissionRepository.isRoleCanAdd(role.getSetting().getTitle(), endpoint)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isUserCanEdit(Long userId, String endpoint) {
        List<UserRole> roles = roleRepository.getRolesByUserId(userId);
        for(UserRole role : roles){
            if(permissionRepository.isRoleCanEdit(role.getSetting().getTitle(), endpoint)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isUserCanDelete(Long userId, String endpoint) {
        List<UserRole> roles = roleRepository.getRolesByUserId(userId);
        for(UserRole role : roles){
            if(permissionRepository.isRoleCanDelete(role.getSetting().getTitle(), endpoint)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<SubHeaderDTO> getListCurriculumHeader(Long userId) {
        List<SubHeaderDTO> subHeader = new ArrayList<>();
        if(isUserAccessAll(userId, URLConstant.Curriculum.OVERVIEW) || isUserCanRead(userId, URLConstant.Curriculum.OVERVIEW)){
            subHeader.add(new SubHeaderDTO("Overview", URLConstant.Curriculum.OVERVIEW));
        }
        if(isUserAccessAll(userId, URLConstant.Curriculum.PLOs) || isUserCanRead(userId, URLConstant.Curriculum.PLOs)){
            subHeader.add(new SubHeaderDTO("PLOs", URLConstant.Curriculum.PLOs));
        }
        if(isUserAccessAll(userId, URLConstant.Curriculum.POs) || isUserCanRead(userId, URLConstant.Curriculum.POs)){
            subHeader.add(new SubHeaderDTO("POs", URLConstant.Curriculum.POs));
        }
        if(isUserAccessAll(userId, URLConstant.Curriculum.PLO_POs) || isUserCanRead(userId, URLConstant.Curriculum.PLO_POs)){
            subHeader.add(new SubHeaderDTO("PLO_POs", URLConstant.Curriculum.PLO_POs));
        }
        if(isUserAccessAll(userId, URLConstant.Curriculum.SUBJECTS) || isUserCanRead(userId, URLConstant.Curriculum.SUBJECTS)){
            subHeader.add(new SubHeaderDTO("Subjects", URLConstant.Curriculum.SUBJECTS));
        }
        if(isUserAccessAll(userId, URLConstant.Curriculum.SUBJECT_PLOS) || isUserCanRead(userId, URLConstant.Curriculum.SUBJECT_PLOS)){
            subHeader.add(new SubHeaderDTO("Subject-PLOs", URLConstant.Curriculum.SUBJECT_PLOS));
        }
        if(isUserAccessAll(userId, URLConstant.Curriculum.COMBOS) || isUserCanRead(userId, URLConstant.Curriculum.COMBOS)){
            subHeader.add(new SubHeaderDTO("Combos", URLConstant.Curriculum.COMBOS));
        }
        if(isUserAccessAll(userId, URLConstant.Curriculum.ELECTIVES) || isUserCanRead(userId, URLConstant.Curriculum.ELECTIVES)){
            subHeader.add(new SubHeaderDTO("Elective", URLConstant.Curriculum.ELECTIVES));
        }
        if(isUserAccessAll(userId, URLConstant.Curriculum.GROUP) || isUserCanRead(userId, URLConstant.Curriculum.GROUP)){
            subHeader.add(new SubHeaderDTO("Group", URLConstant.Curriculum.GROUP));
        }
        return subHeader;
    }

    @Override
    public List<SubHeaderDTO> getListCurriculumHeaderForGuest() {
        List<SubHeaderDTO> subHeader = new ArrayList<>();
        subHeader.add(new SubHeaderDTO("Overview", URLConstant.Curriculum.OVERVIEW));
        subHeader.add(new SubHeaderDTO("PLOs", URLConstant.Curriculum.PLOs));
        subHeader.add(new SubHeaderDTO("POs", URLConstant.Curriculum.POs));
        subHeader.add(new SubHeaderDTO("PLO_POs", URLConstant.Curriculum.PLO_POs));
        subHeader.add(new SubHeaderDTO("Subjects", URLConstant.Curriculum.SUBJECTS));
        subHeader.add(new SubHeaderDTO("Subject-PLOs", URLConstant.Curriculum.SUBJECT_PLOS));
        subHeader.add(new SubHeaderDTO("Combos", URLConstant.Curriculum.COMBOS));
        subHeader.add(new SubHeaderDTO("Elective", URLConstant.Curriculum.ELECTIVES));
        return subHeader;
    }

    @Override
    public List<SubHeaderDTO> getListSyllabusHeader(Long userId) {
        List<SubHeaderDTO> subHeader = new ArrayList<>();
        if(isUserAccessAll(userId, URLConstant.Syllabus.SYLLABUS) || isUserCanRead(userId, URLConstant.Syllabus.SYLLABUS)){
            subHeader.add(new SubHeaderDTO("Overview", URLConstant.Syllabus.SYLLABUS));
        }
        if(isUserAccessAll(userId, URLConstant.Syllabus.SYLLABUS_CLOS) || isUserCanRead(userId, URLConstant.Syllabus.SYLLABUS_CLOS)){
            subHeader.add(new SubHeaderDTO("CLOs", URLConstant.Syllabus.SYLLABUS_CLOS));
        }
        if(isUserAccessAll(userId, URLConstant.Syllabus.SYLLABUS_CLO_PLOS) || isUserCanRead(userId, URLConstant.Syllabus.SYLLABUS_CLO_PLOS)){
            subHeader.add(new SubHeaderDTO("CLO-PLOs", URLConstant.Syllabus.SYLLABUS_CLO_PLOS));
        }
        if(isUserAccessAll(userId, URLConstant.Syllabus.SYLLABUS_MATERIAL) || isUserCanRead(userId, URLConstant.Syllabus.SYLLABUS_MATERIAL)){
            subHeader.add(new SubHeaderDTO("Materials", URLConstant.Syllabus.SYLLABUS_MATERIAL));
        }
        if(isUserAccessAll(userId, URLConstant.Syllabus.SYLLABUS_SCHEDULE) || isUserCanRead(userId, URLConstant.Syllabus.SYLLABUS_SCHEDULE)){
            subHeader.add(new SubHeaderDTO("Schedule", URLConstant.Syllabus.SYLLABUS_SCHEDULE));
        }
        if(isUserAccessAll(userId, URLConstant.Syllabus.SYLLABUS_CQS) || isUserCanRead(userId, URLConstant.Syllabus.SYLLABUS_CQS)){
            subHeader.add(new SubHeaderDTO("CQs", URLConstant.Syllabus.SYLLABUS_CQS));
        }
        if(isUserAccessAll(userId, URLConstant.Syllabus.SYLLABUS_ASSESSMENTS) || isUserCanRead(userId, URLConstant.Syllabus.SYLLABUS_ASSESSMENTS)){
            subHeader.add(new SubHeaderDTO("Assessments", URLConstant.Syllabus.SYLLABUS_ASSESSMENTS));
        }
        return subHeader;
    }

    @Override
    public List<SubHeaderDTO> getListSyllabusHeaderForGuest() {
        List<SubHeaderDTO> subHeader = new ArrayList<>();
        subHeader.add(new SubHeaderDTO("Overview", URLConstant.Syllabus.SYLLABUS));
        subHeader.add(new SubHeaderDTO("CLOs", URLConstant.Syllabus.SYLLABUS_CLOS));
        subHeader.add(new SubHeaderDTO("CLO-PLOs", URLConstant.Syllabus.SYLLABUS_CLO_PLOS));
        subHeader.add(new SubHeaderDTO("Materials", URLConstant.Syllabus.SYLLABUS_MATERIAL));
        subHeader.add(new SubHeaderDTO("Schedule", URLConstant.Syllabus.SYLLABUS_SCHEDULE));
        subHeader.add(new SubHeaderDTO("CQs", URLConstant.Syllabus.SYLLABUS_CQS));
        subHeader.add(new SubHeaderDTO("Assessments", URLConstant.Syllabus.SYLLABUS_ASSESSMENTS));
        return subHeader;
    }
}
