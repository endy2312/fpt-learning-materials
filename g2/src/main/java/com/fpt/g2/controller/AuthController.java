package com.fpt.g2.controller;

import com.fpt.g2.constant.CommonConstant;
import com.fpt.g2.constant.ScreenConstant;
import com.fpt.g2.constant.URLConstant;
import com.fpt.g2.dto.EmailRegisterRequestDTO;
import com.fpt.g2.dto.ForgotPasswordDTO;
import com.fpt.g2.dto.PhoneRegisterRequestDTO;
import com.fpt.g2.dto.UserDetailsDTO;
import com.fpt.g2.entity.User;
import com.fpt.g2.service.EmailService;
import com.fpt.g2.service.SettingService;
import com.fpt.g2.service.SmsService;
import com.fpt.g2.service.UserService;
import com.fpt.g2.service.impl.UserDetailsServiceImpl;
import com.fpt.g2.utils.GooglePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class AuthController {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private SettingService settingService;

    @GetMapping(URLConstant.Authentication.INIT_LOGIN)
    public String initLogin(Principal principal, HttpSession session, Model model, @RequestParam(required = false, defaultValue = "") String error,
                            @RequestParam(value = "username", required = false, defaultValue = "") String username) {
//        session.setAttribute("SPRING_SECURITY_LAST_EXCEPTION",null);
        model.addAttribute(CommonConstant.LOGIN_ERROR, error);
        model.addAttribute(CommonConstant.USERNAME, username);
        session.setAttribute(CommonConstant.LOGIN_ROLES, new ArrayList<>());
        model.addAttribute(CommonConstant.SUB_MENU,CommonConstant.LOGIN_MENU);
        if (principal != null) {
            User user = userDetailsService.getUserByUsername(principal.getName());
            session.setAttribute("avatar", user.getImage());
            session.setAttribute(CommonConstant.ID, user.getId());

            List<String> roles = settingService.getRolesByUserId(user.getId());

            session.setAttribute(CommonConstant.LOGIN_ROLES, roles);
            session.setAttribute(CommonConstant.USERNAME, user.getUsername());
            session.setAttribute(CommonConstant.FULL_NAME, user.getFullName());
            session.setAttribute(CommonConstant.EMAIL, user.getEmail());
            return "redirect:/home";
        }
        return ScreenConstant.HOME_PAGE;
    }


    @GetMapping(URLConstant.Authentication.LOGIN_GOOGLE)
    public String loginGoogle(HttpServletRequest request, Model model, HttpSession session) throws IOException {
        String code = request.getParameter("code");

        if (code == null || code.isEmpty()) {
            return "redirect:/login-google";
        }
        String accessToken = userDetailsService.getToken(code);

        GooglePojo googlePojo = userDetailsService.getUserInfo(accessToken);

        if (!googlePojo.getEmail().endsWith(CommonConstant.EMAIL_FPT)) {
            model.addAttribute(CommonConstant.ATTR_ERROR_MAIL, CommonConstant.MSG_ERROR_MAIL);
            return ScreenConstant.Authentication.LOGIN_PAGE;
        }
        session.setAttribute("avatar", googlePojo.getPicture());
        userDetailsService.saveGooglePojo(googlePojo);
        UserDetails userDetail = userDetailsService.loadUserByUsername(googlePojo.getEmail());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null,
                userDetail.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/";
    }

    @GetMapping(URLConstant.Error.ACCESS_DENIED)
    public String error403() {
        return ScreenConstant.Error.PAGE_403;
    }

    @GetMapping(URLConstant.Error.PAGE_NOT_FOUND)
    public String error404() {
        return ScreenConstant.Error.PAGE_404;
    }

    @GetMapping(URLConstant.Error.INTERNAL_SERVER_ERROR)
    public String error500() {
        return ScreenConstant.Error.PAGE_500;
    }

    @GetMapping(URLConstant.Authentication.REGISTER)
    public String initRegister(@ModelAttribute EmailRegisterRequestDTO registerRequest, Model model,HttpSession session) {
        model.addAttribute(CommonConstant.REGISTER_ATTR, registerRequest);
        model.addAttribute(CommonConstant.SUB_MENU,CommonConstant.EMAIL_REGISTER);
        model.addAttribute(CommonConstant.LOGIN_ROLES, new ArrayList<>());
        session.setAttribute(CommonConstant.LOGIN_ROLES, new ArrayList<>());
        return ScreenConstant.HOME_PAGE;
    }

    @GetMapping(URLConstant.Authentication.PHONE_REGISTER)
    public String initPhoneRegister(@ModelAttribute PhoneRegisterRequestDTO registerRequest, Model model,HttpSession session) {
        model.addAttribute(CommonConstant.REGISTER_ATTR, registerRequest);
        model.addAttribute(CommonConstant.SUB_MENU,CommonConstant.PHONE_REGISTER);
        session.setAttribute(CommonConstant.LOGIN_ROLES, new ArrayList<>());
        return ScreenConstant.HOME_PAGE;
    }

    @PostMapping(URLConstant.Authentication.REGISTER)
    public String register(@ModelAttribute EmailRegisterRequestDTO registerRequest, Model model, HttpSession session) {
        if (session.getAttribute(CommonConstant.VERIFY_CODE) == null
                || (!session.getAttribute(CommonConstant.VERIFY_CODE).equals(registerRequest.getVerifyCode())
                || !session.getAttribute(CommonConstant.SIGNUP_EMAIL).equals(registerRequest.getEmail()))) {
            model.addAttribute(CommonConstant.REGISTER_ERROR, CommonConstant.MSG_VERIFY_FAIL);
        } else if (userService.isUserEmailExisted(registerRequest.getEmail())) {
            model.addAttribute(CommonConstant.REGISTER_ERROR, CommonConstant.MSG_EXISTED_EMAIL);
        } else {
            userService.registerUserByEmail(registerRequest);
            model.addAttribute(CommonConstant.REGISTER_SUCCESSFUL, CommonConstant.MSG_REGISTER_SUCCESSFUL);
        }
        model.addAttribute(CommonConstant.REGISTER_ATTR, registerRequest);
        model.addAttribute(CommonConstant.SUB_MENU,CommonConstant.EMAIL_REGISTER);
        session.setAttribute(CommonConstant.LOGIN_ROLES, new ArrayList<>());
        return ScreenConstant.HOME_PAGE;
    }

    @PostMapping(URLConstant.Authentication.PHONE_REGISTER)
    public String registerPhone(@ModelAttribute PhoneRegisterRequestDTO registerRequest, Model model, HttpSession session) {
        model.addAttribute(CommonConstant.REGISTER_ATTR, registerRequest);

        if (session.getAttribute(CommonConstant.PHONE_VERIFY_CODE) == null
                || (!session.getAttribute(CommonConstant.PHONE_VERIFY_CODE).equals(registerRequest.getVerifyCode())
                || !session.getAttribute(CommonConstant.PHONE_REGISTERED).equals(registerRequest.getPhone()))) {
            model.addAttribute(CommonConstant.REGISTER_ERROR, CommonConstant.MSG_VERIFY_FAIL);
        } else if (userService.isUserPhoneExisted(registerRequest.getPhone())) {
            model.addAttribute(CommonConstant.REGISTER_ERROR, CommonConstant.MSG_EXISTED_PHONE);
        } else {
            userService.registerUserByPhone(registerRequest);
            model.addAttribute(CommonConstant.REGISTER_SUCCESSFUL, CommonConstant.MSG_REGISTER_SUCCESSFUL);
        }
        model.addAttribute(CommonConstant.SUB_MENU,CommonConstant.PHONE_REGISTER);
        session.setAttribute(CommonConstant.LOGIN_ROLES, new ArrayList<>());
        return ScreenConstant.HOME_PAGE;
    }

    @GetMapping(URLConstant.Authentication.LOGOUT)
    public String logout(HttpSession session) {
        SecurityContextHolder.getContext().setAuthentication(null);
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping(URLConstant.Authentication.FORGOT)
    public String initForgotPassword(Model model,HttpSession session) {
        session.setAttribute(CommonConstant.LOGIN_ROLES, new ArrayList<>());
        model.addAttribute(CommonConstant.SUB_MENU,CommonConstant.FORGOT_PASSWORD);
        return ScreenConstant.HOME_PAGE;
    }

    @PostMapping(URLConstant.Authentication.VERIFY_FORGOT)
    @Transactional
    @ResponseBody
    public String verifyCodeForgot(@RequestParam(value = "phone", required = false, defaultValue = "") String phone,
                                   @RequestParam(value = "email", required = false, defaultValue = "") String email, HttpSession session) throws UnsupportedEncodingException {
        if (!userService.isUserEmailExisted(email) && !email.equals(CommonConstant.EMPTY_STRING)) {
            return "Email account not register before";
        } else if (userService.isUserEmailExisted(email) && !email.equals(CommonConstant.EMPTY_STRING)) {
            String verifyCode = emailService.sendSimpleMail(email, CommonConstant.RESET_TYPE);
            session.setAttribute(CommonConstant.VERIFY_CODE, verifyCode);
            session.setAttribute(CommonConstant.SIGNUP_EMAIL, email);
            session.setMaxInactiveInterval(CommonConstant.TIMEOUT_CODE_VERIFY);
        } else if (!userService.isUserPhoneExisted(phone) && !phone.equals(CommonConstant.EMPTY_STRING) && !phone.trim().equals("+84")) {
            return "Phone number not register before";
        } else if (userService.isUserPhoneExisted(phone) && !phone.equals(CommonConstant.EMPTY_STRING) && !phone.trim().equals("+84")) {
            String verifyCode = smsService.sendSMS(phone);
            session.setAttribute(CommonConstant.PHONE_VERIFY_CODE, verifyCode);
            session.setAttribute(CommonConstant.PHONE_REGISTERED, phone);
            session.setMaxInactiveInterval(CommonConstant.TIMEOUT_CODE_VERIFY);
        }
        return ScreenConstant.Authentication.FORGOT;
    }

    @PostMapping(URLConstant.Authentication.FORGOT)
    public String forgot(@ModelAttribute ForgotPasswordDTO dto, Model model, HttpSession session) {
        if (!Objects.isNull(dto.getEmail()) && !dto.getEmail().trim().isEmpty()) {
            if (session.getAttribute(CommonConstant.VERIFY_CODE) == null
                    || (!session.getAttribute(CommonConstant.VERIFY_CODE).equals(dto.getCode())
                    || !session.getAttribute(CommonConstant.SIGNUP_EMAIL).equals(dto.getEmail()))) {
                model.addAttribute(CommonConstant.RESET_ERROR, CommonConstant.MSG_VERIFY_FAIL);
            } else if (!userService.isUserEmailExisted(dto.getEmail().trim())) {
                model.addAttribute(CommonConstant.RESET_ERROR, CommonConstant.MSG_NOT_FOUND_USER);
            } else {
                userService.resetPassword(dto.getEmail(), dto.getPassword());
                model.addAttribute(CommonConstant.RESET_SUCCESSFUL, CommonConstant.MSG_REGISTER_SUCCESSFUL);
            }
        }
        if (!Objects.isNull(dto.getPhone()) && !dto.getPhone().trim().equals("+84")) {
            if (session.getAttribute(CommonConstant.PHONE_VERIFY_CODE) == null
                    || (!session.getAttribute(CommonConstant.PHONE_VERIFY_CODE).equals(dto.getCode())
                    || !session.getAttribute(CommonConstant.PHONE_REGISTERED).equals(dto.getPhone()))) {
                model.addAttribute(CommonConstant.RESET_ERROR, CommonConstant.MSG_VERIFY_FAIL);
            } else if (!userService.isUserPhoneExisted(dto.getPhone().trim())) {
                model.addAttribute(CommonConstant.RESET_ERROR, CommonConstant.MSG_NOT_FOUND_USER);
            } else {
                userService.resetPassword(dto.getPhone(), dto.getPassword());
                model.addAttribute(CommonConstant.RESET_SUCCESSFUL, CommonConstant.MSG_RESET_SUCCESSFUL);
            }
        }
        model.addAttribute(CommonConstant.LOGIN_ROLES, new ArrayList<>());
        model.addAttribute(CommonConstant.SUB_MENU, CommonConstant.FORGOT_PASSWORD);
        return ScreenConstant.HOME_PAGE;
    }

    private void validatePrinciple(Object principal) {
        if (!(principal instanceof UserDetailsDTO)) {
            throw new IllegalArgumentException("Principal can not be null!");
        }
    }
}
