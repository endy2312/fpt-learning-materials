package com.fpt.g2.service.impl;

import com.fpt.g2.constant.CommonConstant;
import com.fpt.g2.constant.URLConstant;
import com.fpt.g2.dto.FeatureDTO;
import com.fpt.g2.entity.Setting;
import com.fpt.g2.repository.PermissionRepository;
import com.fpt.g2.repository.SettingRepository;
import com.fpt.g2.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeatureServiceImpl implements FeatureService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private SettingRepository settingRepository;

    @Override
    public List<FeatureDTO> getAdminFeatures() {
        List<FeatureDTO> features = new ArrayList<>();
        if(permissionRepository.isRoleCanAccessAll(CommonConstant.ROLE_ADMIN, URLConstant.Admin.SYSTEM_SETTINGS) ||
                permissionRepository.isRoleCanRead(CommonConstant.ROLE_ADMIN, URLConstant.Admin.SYSTEM_SETTINGS)){
            FeatureDTO feature = new FeatureDTO();
            Setting setting = settingRepository.getPageByEndpoint(URLConstant.Admin.SYSTEM_SETTINGS);
            feature.setHyperlink(setting.getTitle());
            feature.setUrl(URLConstant.Admin.SYSTEM_SETTINGS);
            features.add(feature);
        }
        if(permissionRepository.isRoleCanAccessAll(CommonConstant.ROLE_ADMIN, URLConstant.Admin.PERMISSION_LIST) ||
                permissionRepository.isRoleCanRead(CommonConstant.ROLE_ADMIN, URLConstant.Admin.PERMISSION_LIST)){
            FeatureDTO feature = new FeatureDTO();
            Setting setting = settingRepository.getPageByEndpoint(URLConstant.Admin.PERMISSION_LIST);
            feature.setHyperlink(setting.getTitle());
            feature.setUrl(URLConstant.Admin.PERMISSION_LIST);
            features.add(feature);
        }
        if(permissionRepository.isRoleCanAccessAll(CommonConstant.ROLE_ADMIN, URLConstant.Admin.ACCOUNT_LIST) ||
                permissionRepository.isRoleCanRead(CommonConstant.ROLE_ADMIN, URLConstant.Admin.ACCOUNT_LIST)){
            FeatureDTO feature = new FeatureDTO();
            Setting setting = settingRepository.getPageByEndpoint(URLConstant.Admin.ACCOUNT_LIST);
            feature.setHyperlink(setting.getTitle());
            feature.setUrl(URLConstant.Admin.ACCOUNT_LIST);
            features.add(feature);
        }
        if(permissionRepository.isRoleCanAccessAll(CommonConstant.ROLE_ADMIN, URLConstant.Subject.SUBJECT_SUCCESSORS) ||
                permissionRepository.isRoleCanRead(CommonConstant.ROLE_ADMIN, URLConstant.Subject.SUBJECT_SUCCESSORS)){
            FeatureDTO feature = new FeatureDTO();
            Setting setting = settingRepository.getPageByEndpoint(URLConstant.Subject.SUBJECT_SUCCESSORS);
            feature.setHyperlink(setting.getTitle());
            feature.setUrl(URLConstant.Subject.SUBJECT_SUCCESSORS);
            features.add(feature);
        }
        return features;
    }

    @Override
    public List<FeatureDTO> getStudentFeatures() {
        List<FeatureDTO> features = new ArrayList<>();
        if(permissionRepository.isRoleCanAccessAll(CommonConstant.ROLE_STUDENT, URLConstant.Curriculum.CURRICULUMS_LIST) ||
                permissionRepository.isRoleCanRead(CommonConstant.ROLE_STUDENT, URLConstant.Curriculum.CURRICULUMS_LIST)){
            FeatureDTO feature = new FeatureDTO();
            Setting setting = settingRepository.getPageByEndpoint(URLConstant.Curriculum.CURRICULUMS_LIST);
            feature.setHyperlink(setting.getTitle());
            feature.setUrl(URLConstant.Curriculum.CURRICULUMS_LIST);
            features.add(feature);
        }
        if(permissionRepository.isRoleCanAccessAll(CommonConstant.ROLE_STUDENT, URLConstant.Syllabus.SYLLABUS_LIST) ||
                permissionRepository.isRoleCanRead(CommonConstant.ROLE_STUDENT, URLConstant.Syllabus.SYLLABUS_LIST)){
            FeatureDTO feature = new FeatureDTO();
            Setting setting = settingRepository.getPageByEndpoint(URLConstant.Syllabus.SYLLABUS_LIST);
            feature.setHyperlink(setting.getTitle());
            feature.setUrl(URLConstant.Syllabus.SYLLABUS_LIST);
            features.add(feature);
        }
        if(permissionRepository.isRoleCanAccessAll(CommonConstant.ROLE_STUDENT, URLConstant.Subject.SUBJECT_PREDECESSORS) ||
                permissionRepository.isRoleCanRead(CommonConstant.ROLE_STUDENT, URLConstant.Subject.SUBJECT_PREDECESSORS)){
            FeatureDTO feature = new FeatureDTO();
            Setting setting = settingRepository.getPageByEndpoint(URLConstant.Subject.SUBJECT_PREDECESSORS);
            feature.setHyperlink(setting.getTitle());
            feature.setUrl(URLConstant.Subject.SUBJECT_PREDECESSORS);
            features.add(feature);
        }
        if(permissionRepository.isRoleCanAccessAll(CommonConstant.ROLE_STUDENT, URLConstant.Subject.SUBJECT_SUCCESSORS) ||
                permissionRepository.isRoleCanRead(CommonConstant.ROLE_STUDENT, URLConstant.Subject.SUBJECT_SUCCESSORS)){
            FeatureDTO feature = new FeatureDTO();
            Setting setting = settingRepository.getPageByEndpoint(URLConstant.Subject.SUBJECT_SUCCESSORS);
            feature.setHyperlink(setting.getTitle());
            feature.setUrl(URLConstant.Subject.SUBJECT_SUCCESSORS);
            features.add(feature);
        }
        return features;
    }

    @Override
    public List<FeatureDTO> getTeacherFeatures() {
        List<FeatureDTO> features = new ArrayList<>();
        if(permissionRepository.isRoleCanAccessAll(CommonConstant.ROLE_TEACHER, URLConstant.Curriculum.CURRICULUMS_LIST) ||
                permissionRepository.isRoleCanRead(CommonConstant.ROLE_TEACHER, URLConstant.Curriculum.CURRICULUMS_LIST)){
            FeatureDTO feature = new FeatureDTO();
            Setting setting = settingRepository.getPageByEndpoint(URLConstant.Curriculum.CURRICULUMS_LIST);
            feature.setHyperlink(setting.getTitle());
            feature.setUrl(URLConstant.Curriculum.CURRICULUMS_LIST);
            features.add(feature);
        }
        if(permissionRepository.isRoleCanAccessAll(CommonConstant.ROLE_TEACHER, URLConstant.Syllabus.SYLLABUS_LIST) ||
                permissionRepository.isRoleCanRead(CommonConstant.ROLE_TEACHER, URLConstant.Syllabus.SYLLABUS_LIST)){
            FeatureDTO feature = new FeatureDTO();
            Setting setting = settingRepository.getPageByEndpoint(URLConstant.Syllabus.SYLLABUS_LIST);
            feature.setHyperlink(setting.getTitle());
            feature.setUrl(URLConstant.Syllabus.SYLLABUS_LIST);
            features.add(feature);
        }
        if(permissionRepository.isRoleCanAccessAll(CommonConstant.ROLE_TEACHER, URLConstant.Subject.SUBJECT_PREDECESSORS) ||
                permissionRepository.isRoleCanRead(CommonConstant.ROLE_TEACHER, URLConstant.Subject.SUBJECT_PREDECESSORS)){
            FeatureDTO feature = new FeatureDTO();
            Setting setting = settingRepository.getPageByEndpoint(URLConstant.Subject.SUBJECT_PREDECESSORS);
            feature.setHyperlink(setting.getTitle());
            feature.setUrl(URLConstant.Subject.SUBJECT_PREDECESSORS);
            features.add(feature);
        }
        if(permissionRepository.isRoleCanAccessAll(CommonConstant.ROLE_TEACHER, URLConstant.Subject.SUBJECT_SUCCESSORS) ||
                permissionRepository.isRoleCanRead(CommonConstant.ROLE_TEACHER, URLConstant.Subject.SUBJECT_SUCCESSORS)){
            FeatureDTO feature = new FeatureDTO();
            Setting setting = settingRepository.getPageByEndpoint(URLConstant.Subject.SUBJECT_SUCCESSORS);
            feature.setHyperlink(setting.getTitle());
            feature.setUrl(URLConstant.Subject.SUBJECT_SUCCESSORS);
            features.add(feature);
        }
        return features;
    }

    @Override
    public List<FeatureDTO> getReviewerFeatures() {
        List<FeatureDTO> features = new ArrayList<>();
        if(permissionRepository.isRoleCanAccessAll(CommonConstant.ROLE_SYLLABUS_REVIEWER, URLConstant.Syllabus.SYLLABUS_LIST) ||
                permissionRepository.isRoleCanRead(CommonConstant.ROLE_SYLLABUS_REVIEWER, URLConstant.Syllabus.SYLLABUS_LIST)){
            FeatureDTO feature = new FeatureDTO();
            Setting setting = settingRepository.getPageByEndpoint(URLConstant.Syllabus.SYLLABUS_LIST);
            feature.setHyperlink(setting.getTitle());
            feature.setUrl(URLConstant.Syllabus.SYLLABUS_LIST);
            features.add(feature);
        }
        return features;
    }

    @Override
    public List<FeatureDTO> getDesignerFeatures() {
        List<FeatureDTO> features = new ArrayList<>();
        if(permissionRepository.isRoleCanAccessAll(CommonConstant.ROLE_SYLLABUS_DESIGNER, URLConstant.Syllabus.SYLLABUS_LIST) ||
                permissionRepository.isRoleCanRead(CommonConstant.ROLE_SYLLABUS_DESIGNER, URLConstant.Syllabus.SYLLABUS_LIST)){
            FeatureDTO feature = new FeatureDTO();
            Setting setting = settingRepository.getPageByEndpoint(URLConstant.Syllabus.SYLLABUS_LIST);
            feature.setHyperlink(setting.getTitle());
            feature.setUrl(URLConstant.Syllabus.SYLLABUS_LIST);
            features.add(feature);
        }
        return features;
    }

    @Override
    public List<FeatureDTO> getCRDDHeadFeatures() {
        List<FeatureDTO> features = new ArrayList<>();
        if(permissionRepository.isRoleCanAccessAll(CommonConstant.ROLE_CRDD_HEAD, URLConstant.Curriculum.CURRICULUMS_LIST) ||
                permissionRepository.isRoleCanRead(CommonConstant.ROLE_CRDD_HEAD, URLConstant.Curriculum.CURRICULUMS_LIST)){
            FeatureDTO feature = new FeatureDTO();
            Setting setting = settingRepository.getPageByEndpoint(URLConstant.Curriculum.CURRICULUMS_LIST);
            feature.setHyperlink(setting.getTitle());
            feature.setUrl(URLConstant.Curriculum.CURRICULUMS_LIST);
            features.add(feature);
        }
        if(permissionRepository.isRoleCanAccessAll(CommonConstant.ROLE_CRDD_HEAD, URLConstant.Syllabus.SYLLABUS_LIST) ||
                permissionRepository.isRoleCanRead(CommonConstant.ROLE_CRDD_HEAD, URLConstant.Syllabus.SYLLABUS_LIST)){
            FeatureDTO feature = new FeatureDTO();
            Setting setting = settingRepository.getPageByEndpoint(URLConstant.Syllabus.SYLLABUS_LIST);
            feature.setHyperlink(setting.getTitle());
            feature.setUrl(URLConstant.Syllabus.SYLLABUS_LIST);
            features.add(feature);
        }
        return features;
    }

    @Override
    public List<FeatureDTO> getCRDDStaffFeatures() {
        List<FeatureDTO> features = new ArrayList<>();
        if(permissionRepository.isRoleCanAccessAll(CommonConstant.ROLE_CRDD_STAFF, URLConstant.Curriculum.CURRICULUMS_LIST) ||
                permissionRepository.isRoleCanRead(CommonConstant.ROLE_CRDD_STAFF, URLConstant.Curriculum.CURRICULUMS_LIST)){
            FeatureDTO feature = new FeatureDTO();
            Setting setting = settingRepository.getPageByEndpoint(URLConstant.Curriculum.CURRICULUMS_LIST);
            feature.setHyperlink(setting.getTitle());
            feature.setUrl(URLConstant.Curriculum.CURRICULUMS_LIST);
            features.add(feature);
        }
        if(permissionRepository.isRoleCanAccessAll(CommonConstant.ROLE_CRDD_STAFF, URLConstant.Syllabus.SYLLABUS_LIST) ||
                permissionRepository.isRoleCanRead(CommonConstant.ROLE_CRDD_STAFF, URLConstant.Syllabus.SYLLABUS_LIST)){
            FeatureDTO feature = new FeatureDTO();
            Setting setting = settingRepository.getPageByEndpoint(URLConstant.Syllabus.SYLLABUS_LIST);
            feature.setHyperlink(setting.getTitle());
            feature.setUrl(URLConstant.Syllabus.SYLLABUS_LIST);
            features.add(feature);
        }
        if(permissionRepository.isRoleCanAccessAll(CommonConstant.ROLE_CRDD_STAFF, URLConstant.Subject.SUBJECT_LIST) ||
                permissionRepository.isRoleCanRead(CommonConstant.ROLE_CRDD_STAFF, URLConstant.Subject.SUBJECT_LIST)){
            FeatureDTO feature = new FeatureDTO();
            Setting setting = settingRepository.getPageByEndpoint(URLConstant.Subject.SUBJECT_LIST);
            feature.setHyperlink(setting.getTitle());
            feature.setUrl(URLConstant.Subject.SUBJECT_LIST);
            features.add(feature);
        }
        if(permissionRepository.isRoleCanAccessAll(CommonConstant.ROLE_CRDD_STAFF, URLConstant.Decision.MATERIAL_DECISIONS) ||
                permissionRepository.isRoleCanRead(CommonConstant.ROLE_CRDD_STAFF, URLConstant.Decision.MATERIAL_DECISIONS)){
            FeatureDTO feature = new FeatureDTO();
            Setting setting = settingRepository.getPageByEndpoint(URLConstant.Decision.MATERIAL_DECISIONS);
            feature.setHyperlink(setting.getTitle());
            feature.setUrl(URLConstant.Decision.MATERIAL_DECISIONS);
            features.add(feature);
        }
        return features;
    }
}
