package com.fpt.g2.service;

import com.fpt.g2.dto.FeatureDTO;

import java.util.List;

public interface FeatureService {
    List<FeatureDTO> getAdminFeatures();

    List<FeatureDTO> getStudentFeatures();

    List<FeatureDTO> getTeacherFeatures();

    List<FeatureDTO> getReviewerFeatures();

    List<FeatureDTO> getDesignerFeatures();

    List<FeatureDTO> getCRDDHeadFeatures();

    List<FeatureDTO> getCRDDStaffFeatures();
}
