package com.fpt.g2.controller;

import com.fpt.g2.constant.CommonConstant;
import com.fpt.g2.constant.ScreenConstant;
import com.fpt.g2.constant.URLConstant;
import com.fpt.g2.dto.RequestCommonDTO;
import com.fpt.g2.dto.UserRequestDTO;
import com.fpt.g2.entity.Permission;
import com.fpt.g2.entity.Setting;
import com.fpt.g2.entity.User;
import com.fpt.g2.service.AdminService;
import com.fpt.g2.service.PermissionService;
import com.fpt.g2.service.SettingService;
import com.fpt.g2.service.UserService;
import com.fpt.g2.utils.CommonUtils;
import com.fpt.g2.utils.user.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserService userService;

    @GetMapping(URLConstant.Admin.ACCOUNT_LIST)
    public String init(Model model,@RequestParam(name = "role", required = false, defaultValue = "All") String role,
                       @RequestParam(name = "status", required = false, defaultValue = "All") String status,
                       @ModelAttribute RequestCommonDTO request, HttpSession session) {
        if (session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());

            if (permissionService.isUserAccessAll(userId, URLConstant.Admin.ACCOUNT_LIST)
                    || permissionService.isUserCanRead(userId, URLConstant.Admin.ACCOUNT_LIST)) {

                List<Setting> roles = settingService.getRoles();

                Permission permission = permissionService.getPermission("Admin", URLConstant.Admin.ACCOUNT_LIST);
                model.addAttribute("permission", permission);
                model.addAttribute("users", adminService.getAllUser(request, status.equalsIgnoreCase("All") ? null : status, role.equalsIgnoreCase("All") ? null : role));
                model.addAttribute("roles", roles);
                model.addAttribute("selectedRole", role);
                model.addAttribute("selectedStatus", status);
                model.addAttribute("request", request);
                model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
                model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Admin.ACCOUNT_LIST);
                model.addAttribute("pUrl", URLConstant.Admin.ACCOUNT_LIST);
                return ScreenConstant.HOME_PAGE;
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }

    @GetMapping(URLConstant.Admin.ACCOUNT_DETAIL)
    public String viewUserDetails(@RequestParam("id") Long id, Model model, HttpSession session) {
        if (session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());

            if (permissionService.isUserAccessAll(userId, URLConstant.Admin.ACCOUNT_LIST) || permissionService.isUserCanRead(userId, URLConstant.Admin.ACCOUNT_LIST)) {
                User user = adminService.getUserDetails(id);
                List<String> userRoleNames = settingService.getRolesByUserId(user.getId());
                List<Setting> roleList = settingService.getRoles();
                model.addAttribute("roleList", roleList);
                model.addAttribute("userRoleNames", userRoleNames);

                model.addAttribute("user", user);
                model.addAttribute("userForm", UserUtils.convertToUserForm(user));
                model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
                model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Admin.ACCOUNT_DETAIL);
                model.addAttribute("pUrl", URLConstant.Admin.ACCOUNT_LIST);
                return "page/home";
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }

    @RequestMapping(URLConstant.Admin.ACCOUNT_INSERT)
    public String updateAccount(@ModelAttribute UserRequestDTO userRequestDTO, Model model, HttpSession session) {
        User user;
        String page = ScreenConstant.HOME_PAGE;
        String title = CommonConstant.EMPTY_STRING;
        List<String> userRoleNames = new ArrayList<>();
        model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
        model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.USER_PROFILE);
        model.addAttribute("pUrl", URLConstant.UPDATE);

        if (userRequestDTO.getId() != null && !CommonUtils.isNullOrEmpty(userRequestDTO.getMobile()) && userService.isUserPhoneExisted(userRequestDTO.getMobile())) {
            model.addAttribute("errorMobileMessage", "Mobile already exists in the system");
        }

        if (userRequestDTO.getId() == null &&!CommonUtils.isNullOrEmpty(userRequestDTO.getEmail()) && !userRequestDTO.getEmail().endsWith(CommonConstant.EMAIL_FPT)) {
            model.addAttribute("errorEmailMessage", "Email format is not '@fpt.edu.vn'!");
        }

        if (userRequestDTO.getId() == null &&!CommonUtils.isNullOrEmpty(userRequestDTO.getEmail()) && userService.isUserEmailExisted(userRequestDTO.getEmail())) {
            model.addAttribute("errorEmailMessage", "Email already exists in the system!");
        }

        if (!CommonUtils.isNullOrEmpty((String) session.getAttribute(CommonConstant.PHONE_VERIFY_CODE))) {
            if (!session.getAttribute(CommonConstant.PHONE_VERIFY_CODE).equals(userRequestDTO.getVerifyCode())
                    || !session.getAttribute(CommonConstant.PHONE_REGISTERED).equals(userRequestDTO.getMobile())) {
                model.addAttribute(CommonConstant.UPDATE_PROFILE_ERROR, CommonConstant.MSG_VERIFY_FAIL);
                session.removeAttribute(CommonConstant.PHONE_VERIFY_CODE);
                session.removeAttribute(CommonConstant.PHONE_REGISTERED);
            }
        }

        if (!CommonUtils.isNullOrEmpty((String) session.getAttribute(CommonConstant.VERIFY_CODE))) {
            if (!session.getAttribute(CommonConstant.VERIFY_CODE).equals(userRequestDTO.getVerifyCode())
                    || !session.getAttribute(CommonConstant.SIGNUP_EMAIL).equals(userRequestDTO.getEmail())) {
                model.addAttribute(CommonConstant.UPDATE_PROFILE_ERROR, CommonConstant.MSG_VERIFY_FAIL);
                session.removeAttribute(CommonConstant.VERIFY_CODE);
                session.removeAttribute(CommonConstant.SIGNUP_EMAIL);
            }
        }

        if (session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            List<String> loginRoles = settingService.getRolesByUserId(userId);
            model.addAttribute(CommonConstant.LOGIN_ROLES, loginRoles);
            List<Setting> roleList = settingService.getRoles();
            model.addAttribute("roleList", roleList);
            if (permissionService.isUserAccessAll(userId, URLConstant.Admin.ACCOUNT_LIST) || permissionService.isUserCanRead(userId, URLConstant.Admin.ACCOUNT_LIST)) {
                if (userRequestDTO.getId() != null) {
                    title = "isUpdate";
                    if (CommonUtils.isNullOrEmpty(userRequestDTO.getFullName()) &&
                            CommonUtils.isNullOrEmpty(userRequestDTO.getEmail()) &&
                            CommonUtils.isNullOrEmpty(userRequestDTO.getUsername()) &&
                            CommonUtils.isNullOrEmpty(userRequestDTO.getVerifyCode())) {
                        user = adminService.getUserDetails(userRequestDTO.getId());
                        userRoleNames = settingService.getRolesByUserId(user.getId());
                        userRequestDTO = UserUtils.convertToUserForm(user);
                    } else {
                        if (model.getAttribute("errorMobileMessage") == null) {

                            userRequestDTO.setSignupType(CommonConstant.SIGNUP_EMAIL);
                            userService.saveOrUpdate(userRequestDTO, "admin");
                            session.removeAttribute(CommonConstant.VERIFY_CODE);
                            session.removeAttribute(CommonConstant.SIGNUP_EMAIL);
                            page = "redirect:/accounts";
                        }
                    }
                } else {
                    title = "isCreate";
                    if (CommonUtils.isNullOrEmpty(userRequestDTO.getFullName()) &&
                            CommonUtils.isNullOrEmpty(userRequestDTO.getEmail()) &&
                            CommonUtils.isNullOrEmpty(userRequestDTO.getUsername()) &&
                            CommonUtils.isNullOrEmpty(userRequestDTO.getVerifyCode())) {
                        userRequestDTO = new UserRequestDTO();
                    } else if (model.getAttribute("errorEmailMessage") == null) {
                        userRequestDTO.setSignupType(CommonConstant.SIGNUP_EMAIL);
                        userRequestDTO.setPassword((String) session.getAttribute(CommonConstant.PASSWORD));
                        userService.saveOrUpdate(userRequestDTO, "admin");
                        session.removeAttribute(CommonConstant.VERIFY_CODE);
                        session.removeAttribute(CommonConstant.SIGNUP_EMAIL);
                        page = "redirect:/accounts";
                    }
                }
            }
            model.addAttribute("userForm", userRequestDTO);
            model.addAttribute("userRoleNames", userRoleNames);
            model.addAttribute("title", title);
            model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
            model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.USER_PROFILE);
            model.addAttribute("pUrl", URLConstant.Admin.ACCOUNT_LIST);
            return page;
        }
        return ScreenConstant.Error.PAGE_401;
    }

    @GetMapping(URLConstant.Admin.ACCOUNT_STATUS_CHANGE)
    public String deleteAccount(Model model, @RequestParam("id") Long id, HttpSession session) {
        if (session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            if (permissionService.isUserAccessAll(userId, URLConstant.Admin.ACCOUNT_LIST) || permissionService.isUserCanRead(userId, URLConstant.Admin.ACCOUNT_LIST)) {
                String message = userService.lockOrUnlockAccount(id);
                return "redirect:/accounts";
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }

}
