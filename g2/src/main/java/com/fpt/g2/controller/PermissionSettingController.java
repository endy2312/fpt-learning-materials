package com.fpt.g2.controller;

import com.fpt.g2.constant.CommonConstant;
import com.fpt.g2.constant.ScreenConstant;
import com.fpt.g2.constant.URLConstant;
import com.fpt.g2.dto.PermissionDTO;
import com.fpt.g2.dto.PermissionsRqDTO;
import com.fpt.g2.entity.Permission;
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

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PermissionSettingController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private SettingService settingService;

    @GetMapping(URLConstant.Admin.PERMISSION_LIST)
    public String init(Model model, @ModelAttribute PermissionDTO request, HttpSession session) {
        if(session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            if (permissionService.isUserAccessAll(userId, URLConstant.Admin.PERMISSION_LIST)
                    || permissionService.isUserCanRead(userId, URLConstant.Admin.PERMISSION_LIST)) {
                Page permissions = permissionService.getPermissions(request);
                model.addAttribute("permissions", permissions);
                model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
                model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Admin.PERMISSION_SETTING);
                model.addAttribute(CommonConstant.REQUEST, request);
                model.addAttribute("pageUrl", URLConstant.Admin.PERMISSION_LIST);

                List<Setting> roles = settingService.getRoles();
                model.addAttribute("roles", roles);
                return ScreenConstant.HOME_PAGE;
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }

    @PostMapping(URLConstant.Admin.PERMISSION_LIST + URLConstant.UPDATE)
    public String updatePermission(@ModelAttribute("pageContent") PermissionsRqDTO pageContent, HttpSession session){
        if(session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            if (permissionService.isUserCanEdit(userId, URLConstant.Admin.PERMISSION_LIST)) {
                List<Permission> permissions = pageContent.getPermissionRq();
                for(Permission permission : permissions){
                    permissionService.updatePermission(permission, userId);
                }
                return "redirect:/dashboard/permissions";
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }
}
