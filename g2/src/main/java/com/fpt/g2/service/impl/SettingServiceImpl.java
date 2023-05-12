package com.fpt.g2.service.impl;

import com.fpt.g2.dto.SettingDTO;
import com.fpt.g2.dto.SettingRequestDTO;
import com.fpt.g2.entity.Setting;
import com.fpt.g2.entity.UserRole;
import com.fpt.g2.repository.SettingRepository;
import com.fpt.g2.repository.UserRoleRepository;
import com.fpt.g2.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SettingServiceImpl implements SettingService {

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public List<Setting> getRoles() {
        return settingRepository.getRoles();
    }

    @Override
    public List<String> getRolesByUserId(Long id) {
        List<String> roles = new ArrayList<>();
        List<UserRole> userRoles = userRoleRepository.getRolesByUserId(id);
        for(UserRole userRole : userRoles){
            roles.add(userRole.getSetting().getTitle());
        }
        return roles;
    }

    @Override
    public List<Long> getRolesByUserIds(Long id) {
        List<Long> roles = new ArrayList<>();
        List<UserRole> userRoles = userRoleRepository.getRolesByUserId(id);
        for(UserRole userRole : userRoles){
            roles.add(userRole.getSetting().getId());
        }
        return roles;
    }

    @Override
    public Page getSettings(SettingDTO request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(),request.getSortBy());
        String keyword = null;
        if(request.getKeyword() != null && !request.getKeyword().isEmpty()){
            keyword = "%" + request.getKeyword() + "%";
        }
        if(request.getStatus() != null && !request.getStatus().isEmpty()) {
            if (request.getStatus().equals("Active")) {
                return settingRepository.getSettings(request.getType(), keyword, false, pageable);
            }
            if (request.getStatus().equals("Deactive")) {
                return settingRepository.getSettings(request.getType(), keyword, true, pageable);
            }
        }
        return settingRepository.getSettings(request.getType(), keyword, null, pageable);
    }

    @Override
    public List<String> getTypes() {
        return settingRepository.getTypes();
    }

    @Override
    public Setting findSettingById(Long id) {
        return settingRepository.getById(id);
    }

    @Override
    public void insertSetting(Long id, SettingRequestDTO setting, Long userId) {
        Setting st;
        if(id == null || id <= 0){
            st = new Setting();
        } else {
            st = settingRepository.getById(id);
        }
        if(setting.getType().trim().equals("blank")){
            st.setType(null);
        } else {
            st.setType(setting.getType());
        }
        st.setTitle(setting.getTitle());
        st.setValue(setting.getValue());
        st.setDisplayOrder(setting.getDisplayOrder());
        if(setting.getStatus().equals("Active")){
            st.setDeleteFlag(false);
        } else {
            st.setDeleteFlag(true);
        }
        st.setCreatedBy(userId);
        settingRepository.save(st);
    }

    @Override
    public void changeSettingStatus(Long id, Long userId) {
        Setting setting = settingRepository.getById(id);
        if(setting != null){
            if(setting.isDeleteFlag()){
                setting.setDeleteFlag(false);
            } else {
                setting.setDeleteFlag(true);
            }
            setting.setUpdatedBy(userId);
            settingRepository.save(setting);
        }
    }

    @Override
    public List<Setting> getDegreeLevels() {
        return settingRepository.getDegreeLevels();
    }
}
