package com.fpt.g2.controller;

import com.fpt.g2.constant.CommonConstant;
import com.fpt.g2.entity.EmailDetails;
import com.fpt.g2.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class EmailController {
    @Autowired
    private EmailService emailService;

    // Sending a simple Email
    @PostMapping("/sendMail")
    @ResponseBody
    public ResponseEntity sendMail(@RequestBody EmailDetails details, HttpSession session) {
        String verifyCode = emailService.sendSimpleMail(details.getRecipient(),CommonConstant.VERIFY_TYPE);
        session.setAttribute(CommonConstant.VERIFY_CODE, verifyCode);
        session.setAttribute(CommonConstant.SIGNUP_EMAIL, details.getRecipient());
        session.setMaxInactiveInterval(CommonConstant.TIMEOUT_CODE_VERIFY);
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @PostMapping("/sendMailWithPassword")
    @ResponseBody
    public ResponseEntity sendVerifyCodeAndPassword(@RequestBody EmailDetails details, HttpSession session) {
        String[] verifyCode = emailService.sendSimpleMailWithPassword(details.getRecipient()).split("-");
        session.setAttribute(CommonConstant.VERIFY_CODE, verifyCode[0]);
        session.setAttribute(CommonConstant.PASSWORD, verifyCode[1]);
        session.setAttribute(CommonConstant.SIGNUP_EMAIL, details.getRecipient());
        session.setMaxInactiveInterval(CommonConstant.TIMEOUT_CODE_VERIFY);
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
