package com.fpt.g2.service;

import java.io.UnsupportedEncodingException;

public interface SmsService {
    String sendSMS(String receiver) throws UnsupportedEncodingException;
}
