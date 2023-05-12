package com.fpt.g2.controller;

import com.fpt.g2.constant.CommonConstant;
import com.fpt.g2.constant.ScreenConstant;
import com.fpt.g2.constant.URLConstant;
import com.fpt.g2.dto.SettingDTO;
import com.fpt.g2.dto.SettingRequestDTO;
import com.fpt.g2.entity.Setting;
import com.fpt.g2.service.PermissionService;
import com.fpt.g2.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class SystemSettingController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private SettingService settingService;

    @GetMapping(URLConstant.Admin.SYSTEM_SETTINGS)
    public String init(Model model, @ModelAttribute SettingDTO request, HttpSession session) {
        if(session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            if (permissionService.isUserAccessAll(userId, URLConstant.Admin.SYSTEM_SETTINGS)
                    || permissionService.isUserCanRead(userId, URLConstant.Admin.SYSTEM_SETTINGS)) {

                if(request.getSortBy().toString().split(":")[0].equals("updatedDate")) {
                    request.setSortBy("type");
                    request.setSortType("desc");
                }
                Page settings = settingService.getSettings(request);
                model.addAttribute("settings", settings);
                model.addAttribute("settingsContent", settings.getContent());
                model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
                model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Admin.SYSTEM_SETTING);
                model.addAttribute(CommonConstant.REQUEST, request);
                model.addAttribute("pageUrl", URLConstant.Admin.SYSTEM_SETTINGS);
                model.addAttribute("canAdd", permissionService.isUserCanAdd(userId, URLConstant.Admin.SYSTEM_SETTINGS));

                List<String> types = settingService.getTypes();
                model.addAttribute("types", types);
                return ScreenConstant.HOME_PAGE;
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }

    @GetMapping(URLConstant.Admin.SYSTEM_SETTINGS + URLConstant.INSERT)
    public String insertSetting(Model model, HttpSession session, @RequestParam(value = "id", required = false) Long id){
        if(session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            if(permissionService.isUserAccessAll(userId, URLConstant.Admin.SYSTEM_SETTINGS)
                    || permissionService.isUserCanRead(userId, URLConstant.Admin.SYSTEM_SETTINGS)){
                if(id == null || id <= 0){
                    if (permissionService.isUserCanAdd(userId, URLConstant.Admin.SYSTEM_SETTINGS)) {
                        Setting setting = new Setting();
                        model.addAttribute(CommonConstant.SETTING, setting);
                    } else {
                        return ScreenConstant.Error.PAGE_401;
                    }
                } else {
                    if (permissionService.isUserCanEdit(userId, URLConstant.Admin.SYSTEM_SETTINGS)) {
                        Setting setting = settingService.findSettingById(id);
                        model.addAttribute(CommonConstant.SETTING, setting);
                    } else {
                        return ScreenConstant.Error.PAGE_401;
                    }
                }
                model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
                model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Admin.NEW_SETTING);
                List<String> types = settingService.getTypes();
                model.addAttribute("systemTypes", types);
                return ScreenConstant.HOME_PAGE;
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }

    @PostMapping(URLConstant.Admin.SYSTEM_SETTINGS + URLConstant.INSERT)
    public String insertSetting(Model model, HttpSession session, @RequestParam(value = "id", required = false) Long id, @ModelAttribute SettingRequestDTO request){
        if(session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            if(permissionService.isUserAccessAll(userId, URLConstant.Admin.SYSTEM_SETTINGS)
                    || permissionService.isUserCanRead(userId, URLConstant.Admin.SYSTEM_SETTINGS)){
                if(id == null || id <= 0){
                    if (permissionService.isUserCanAdd(userId, URLConstant.Admin.SYSTEM_SETTINGS)) {
                        settingService.insertSetting(id, request, userId);
                    } else {
                        return ScreenConstant.Error.PAGE_401;
                    }
                } else {
                    if (permissionService.isUserCanEdit(userId, URLConstant.Admin.SYSTEM_SETTINGS)) {
                        settingService.insertSetting(id, request, userId);
                    } else {
                        return ScreenConstant.Error.PAGE_401;
                    }
                }
                return "redirect:/settings";
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }

    @GetMapping(URLConstant.Admin.SETTING_STATUS_CHANGE)
    public String changeSettingStatus(Model model, HttpSession session, @RequestParam("id") Long id){
        if(session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            if((permissionService.isUserAccessAll(userId, URLConstant.Admin.SYSTEM_SETTINGS)
                    || permissionService.isUserCanRead(userId, URLConstant.Admin.SYSTEM_SETTINGS))
                    && permissionService.isUserCanEdit(userId, URLConstant.Admin.SYSTEM_SETTINGS)){
                settingService.changeSettingStatus(id, userId);
                return "redirect:/settings";
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }
}
