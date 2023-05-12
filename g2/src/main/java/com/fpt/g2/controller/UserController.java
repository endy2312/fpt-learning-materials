package com.fpt.g2.controller;

import com.fpt.g2.constant.CommonConstant;
import com.fpt.g2.constant.ScreenConstant;
import com.fpt.g2.constant.URLConstant;
import com.fpt.g2.dto.UploadDTO;
import com.fpt.g2.dto.UserRequestDTO;
import com.fpt.g2.entity.Setting;
import com.fpt.g2.entity.User;
import com.fpt.g2.service.SettingService;
import com.fpt.g2.service.UserService;
import com.fpt.g2.service.impl.UserDetailsServiceImpl;
import com.fpt.g2.utils.CommonUtils;
import com.fpt.g2.utils.user.UserUtils;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private SettingService settingService;


    @GetMapping(URLConstant.PROFILE)
    public String init(Model model, HttpSession session) {
        if (session.getAttribute(CommonConstant.ID) != null) {
            UserRequestDTO userRequestDTO = UserUtils.convertToUserForm(userService.findById((Long) session.getAttribute(CommonConstant.ID)));
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            List<String> loginRoles = settingService.getRolesByUserId(userId);
            model.addAttribute(CommonConstant.LOGIN_ROLES, loginRoles);
            List<String> userRoleNames = settingService.getRolesByUserId(userId);
            List<Setting> roleList = settingService.getRoles();
            model.addAttribute("roleList", roleList);
            model.addAttribute("userRoleNames", userRoleNames);
            model.addAttribute("userForm", userRequestDTO);
            model.addAttribute("title", "User profile");
            model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
            model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.USER_PROFILE);
            return ScreenConstant.HOME_PAGE;
        }
        return ScreenConstant.Error.PAGE_403;
    }

    @PostMapping(path = URLConstant.PROFILE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateUserProfile(@ModelAttribute("userForm") UserRequestDTO userRequestDTO, HttpSession session, Model model, HttpServletRequest request) {
        if (session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            User user = null;

            if (!CommonUtils.isNullOrEmpty((String) session.getAttribute(CommonConstant.PHONE_VERIFY_CODE))) {
                if (!session.getAttribute(CommonConstant.PHONE_VERIFY_CODE).equals(userRequestDTO.getVerifyCode())
                        || !session.getAttribute(CommonConstant.PHONE_REGISTERED).equals(userRequestDTO.getMobile())) {
                    model.addAttribute(CommonConstant.UPDATE_PROFILE_ERROR, CommonConstant.MSG_VERIFY_FAIL);
                }
            }

            if (!CommonUtils.isNullOrEmpty((String) session.getAttribute(CommonConstant.VERIFY_CODE))) {
                if (!session.getAttribute(CommonConstant.VERIFY_CODE).equals(userRequestDTO.getVerifyCode())
                        || !session.getAttribute(CommonConstant.SIGNUP_EMAIL).equals(userRequestDTO.getEmail())) {
                    model.addAttribute(CommonConstant.UPDATE_PROFILE_ERROR, CommonConstant.MSG_VERIFY_FAIL);
                }
            }

            if (userRequestDTO.getSignupType().equals("mobile") && !CommonUtils.isNullOrEmpty(userRequestDTO.getEmail())
                    && userService.isUserPhoneExisted(userRequestDTO.getEmail())
                    && !userService.findById(userRequestDTO.getId()).getEmail().equals(userRequestDTO.getEmail())) {
                model.addAttribute(CommonConstant.UPDATE_PROFILE_ERROR, CommonConstant.MSG_EXISTED_EMAIL);
            }

            if (userRequestDTO.getSignupType().equals("google") && !CommonUtils.isNullOrEmpty(userRequestDTO.getMobile())
                    && userService.isUserPhoneExisted(userRequestDTO.getMobile())
                    && !userService.findById(userRequestDTO.getId()).getMobile().equals(userRequestDTO.getMobile())) {
                model.addAttribute(CommonConstant.UPDATE_PROFILE_ERROR, CommonConstant.MSG_EXISTED_PHONE);
            }

            userRequestDTO.setVerifyCode(null);
            List<Setting> roleList = settingService.getRoles();
            model.addAttribute(CommonConstant.LOGIN_ROLES, session.getAttribute(CommonConstant.LOGIN_ROLES));
            model.addAttribute("title", "User profile");
            model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
            model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.USER_PROFILE);
            model.addAttribute("roleList", roleList);
            model.addAttribute("userForm", userRequestDTO);

            if (!CommonUtils.isNullOrEmpty((String) model.getAttribute(CommonConstant.UPDATE_PROFILE_ERROR))) {
                return ScreenConstant.HOME_PAGE;
            } else {
                user = userService.saveOrUpdate(userRequestDTO, "profile");
            }

            UserDetails userDetail = userDetailsService.loadUserByUsername(user.getEmail());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null,
                    userDetail.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            session.setAttribute(CommonConstant.USERNAME, user.getUsername());
            session.setAttribute(CommonConstant.FULL_NAME, user.getFullName());
            session.setAttribute(CommonConstant.EMAIL, user.getEmail());

            userRequestDTO.setId((Long) session.getAttribute(CommonConstant.ID));
            model.addAttribute(CommonConstant.UPDATE_PROFILE_SUCCESSFUL, "Update profile successful");
            return ScreenConstant.HOME_PAGE;
        }
        return ScreenConstant.Error.PAGE_403;
    }
}
