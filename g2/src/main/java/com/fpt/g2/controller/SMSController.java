package com.fpt.g2.controller;

import com.fpt.g2.constant.CommonConstant;
import com.fpt.g2.dto.SmsSendRequest;
import com.fpt.g2.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/sms")
public class SMSController {

    @Autowired
    SmsService smsService;

    @PostMapping("/process")
    @ResponseBody
    public void processSMS(@RequestBody SmsSendRequest request, HttpSession session) throws UnsupportedEncodingException {
        String code = smsService.sendSMS(request.getDestination());
        session.setAttribute(CommonConstant.PHONE_VERIFY_CODE,code);
        session.setAttribute(CommonConstant.PHONE_REGISTERED,request.getDestination());
        session.setMaxInactiveInterval(CommonConstant.TIMEOUT_CODE_VERIFY);
    }
}
