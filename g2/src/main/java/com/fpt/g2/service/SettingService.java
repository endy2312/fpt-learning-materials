package com.fpt.g2.service;

import com.fpt.g2.dto.SettingDTO;
import com.fpt.g2.dto.SettingRequestDTO;
import com.fpt.g2.entity.Setting;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SettingService {
    List<Setting> getRoles();

    List<String> getRolesByUserId(Long id);

    List<Long> getRolesByUserIds(Long id);

    Page getSettings(SettingDTO request);

    List<String> getTypes();

    Setting findSettingById(Long id);

    void insertSetting(Long id, SettingRequestDTO setting, Long userId);

    void changeSettingStatus(Long id, Long userId);

    List<Setting> getDegreeLevels();
}
