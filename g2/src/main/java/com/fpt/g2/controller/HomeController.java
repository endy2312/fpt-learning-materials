package com.fpt.g2.controller;

import com.fpt.g2.constant.CommonConstant;
import com.fpt.g2.constant.ScreenConstant;
import com.fpt.g2.constant.URLConstant;
import com.fpt.g2.dto.FeatureDTO;
import com.fpt.g2.service.FeatureService;
import com.fpt.g2.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(URLConstant.HOME)
public class HomeController {

    @Autowired
    private FeatureService featureService;

    @Autowired
    private SettingService settingService;

    @GetMapping
    public String initHome(Model model, HttpSession session) {
        if(session.getAttribute(CommonConstant.ID) != null) {
            List<String> roles = settingService.getRolesByUserId(Long.valueOf(session.getAttribute(CommonConstant.ID).toString()));
            if (roles.contains(CommonConstant.ROLE_ADMIN)) {
                List<FeatureDTO> features = featureService.getAdminFeatures();
                model.addAttribute("adminFeatures", features);
            }
            if (roles.contains(CommonConstant.ROLE_STUDENT)) {
                List<FeatureDTO> features = featureService.getStudentFeatures();
                model.addAttribute("studentFeatures", features);
            }
            if (roles.contains(CommonConstant.ROLE_TEACHER)) {
                List<FeatureDTO> features = featureService.getTeacherFeatures();
                model.addAttribute("teacherFeatures", features);
            }
            if (roles.contains(CommonConstant.ROLE_SYLLABUS_REVIEWER)) {
                List<FeatureDTO> features = featureService.getReviewerFeatures();
                model.addAttribute("reviewerFeatures", features);
            }
            if (roles.contains(CommonConstant.ROLE_SYLLABUS_DESIGNER)) {
                List<FeatureDTO> features = featureService.getDesignerFeatures();
                model.addAttribute("designerFeatures", features);
            }
            if (roles.contains(CommonConstant.ROLE_CRDD_STAFF)) {
                List<FeatureDTO> features = featureService.getCRDDStaffFeatures();
                model.addAttribute("staffFeatures", features);
            }
            if (roles.contains(CommonConstant.ROLE_CRDD_HEAD)) {
                List<FeatureDTO> features = featureService.getCRDDHeadFeatures();
                model.addAttribute("headFeatures", features);
            }
            model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.FEATURES);
            return ScreenConstant.HOME_PAGE;
        }
        return "redirect:/";
    }
}
