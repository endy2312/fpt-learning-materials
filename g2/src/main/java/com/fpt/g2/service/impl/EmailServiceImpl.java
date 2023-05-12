package com.fpt.g2.service.impl;

import com.fpt.g2.constant.CommonConstant;
import com.fpt.g2.entity.EmailDetails;
import com.fpt.g2.service.EmailService;
import com.fpt.g2.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public String sendSimpleMail(String recipient, String type) {
        // Try block to check for exceptions
        try {
            String verifyCode = CommonUtils.randomString();
            StringBuilder content = new StringBuilder();
            content.append("<table align=\"center\" class=\"m_-1353841121882709300w-100p\" width=\"476\" style=\"max-width:476px;margin:0 auto\" cellpadding=\"0\" cellspacing=\"0\"> \n" +
                    "<tbody> \n" +
                    "<tr> \n" +
                    "<td align=\"center\" style=\"font-weight:bold;font-size:20px;line-height:32px;font-family:'Whyte',Arial,Helvetica,sans-serif;color:#000;padding:10px 0 32px;text-align:center\"> <h1 style=\"font-weight:bold;font-size:24px;line-height:32px;font-family:'Whyte',Arial,Helvetica,sans-serif;color:#000;text-align:center\">Welcome to FPT Learning Material</h1> </td> \n" +
                    "</tr> \n" +
                    "<tr> \n" +
                    "<td align=\"center\" style=\"color:#000;font-size:18px;line-height:26px;font-family:'Whyte',Arial,Helvetica,sans-serif;text-align:left\"> <p style=\"font-size:18px;line-height:26px;font-family:'Whyte',Helvetica,Arial,sans-serif;color:#000000;font-weight:normal;margin:0\">");
            if (type.equals(CommonConstant.VERIFY_TYPE)) {
                content.append("Here's your verification code: ");
            } else {
                content.append("Here's your password: ");
            }
            content.append(
                    "</p></td> \n" +
                            "</tr> \n" +
                            "<tr> \n" +
                            "<td class=\"m_-1353841121882709300pb-60\" align=\"center\" style=\"padding:50px 0 60px\"> \n" +
                            "<table align=\"center\" style=\"margin:0 auto\" cellpadding=\"0\" cellspacing=\"0\"> \n" +
                            "<tbody> \n" +
                            "<tr> \n" +
                            "<td class=\"m_-1353841121882709300active-t\" align=\"center\" style=\"background-color:#ffffff;font-weight:bold;font-size:20px;line-height:22px;font-family:'Whyte',Helvetica,Arial,sans-serif;border:3px solid #000000;border-radius:5px;padding: 20px 50px\">\n");

            content.append(verifyCode);
            content.append("</td></tr> \n" +
                    "</tbody> \n" +
                    "</table> </td> \n" +
                    "</tr> \n" +
                    " \n" +
                    "<tr> \n" +
                    "<td> \n" +
                    "<table class=\"m_-1353841121882709300w-100p\" width=\"540\" align=\"center\" style=\"max-width:540px;margin:0 auto\" cellpadding=\"0\" cellspacing=\"0\"> \n" +
                    "<tbody> \n" +
                    "<tr> \n" +
                    "<td class=\"m_-1353841121882709300pt-60\" align=\"center\" style=\"border-top:1px solid #dedede;color:#a5a5a5;font-size:14px;line-height:18px;font-family:'Whyte',Arial,Helvetica,sans-serif;text-align:center;padding:60px 24px 32px\">FPT Learning Material</td> \n" +
                    "</tr> \n" +
                    "</tbody> \n" +
                    "</table> </td> \n" +
                    "</tr> \n" +
                    "<tr> \n" +
                    "<td align=\"center\" style=\"padding:10px 24px 46px\"> \n" +
                    "<table width=\"300\" style=\"max-width:300px;margin:0 auto\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\"> \n" +
                    "<tbody> \n" +
                    "<tr> \n" +
                    "<td class=\"m_-1353841121882709300active-i\" style=\"line-height:1px;font-size:1px\"> <a style=\"text-decoration:none\" href=\"https://go.figma.com/Nzc4LU1FVS0yODEAAAGDPDHbKwP3FjWoZlD8r70XWj-FSM2MnkC3_Yw3neOzjfsRzj38DDaMF5q5l-fMkOefkCY5FIk=\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=https://go.figma.com/Nzc4LU1FVS0yODEAAAGDPDHbKwP3FjWoZlD8r70XWj-FSM2MnkC3_Yw3neOzjfsRzj38DDaMF5q5l-fMkOefkCY5FIk%3D&amp;source=gmail&amp;ust=1681573591757000&amp;usg=AOvVaw0JCGz_ZBkpZfC8U5l5lKOh\"><img src=\"https://ci6.googleusercontent.com/proxy/6yF-wMiPnkSHk8Pr0YrYguIcQXiR51LtC16Lfunic4SwtE5JYf5_2nkUHHmNryVQc_6uMGIb9GFgrxWRl1v78qAeIArHcFOwykd3V7vCkPe-hY9Gysy3Z-_a0g0=s0-d-e1-ft#https://static.figma.com/uploads/362dad2073b1e974b85e78019adb0badc946df74\" width=\"30\" style=\"width:30px;max-width:30px;font-size:10px;line-height:12px;font-family:'Whyte',Arial,Helvetica,sans-serif;color:#000;background-color:#fff;vertical-align:top;text-align:center\" alt=\"figma\" class=\"CToWUd\" data-bit=\"iit\"></a></td> \n" +
                    "<td width=\"30\" style=\"font-size:1px;line-height:1px\"></td> \n" +
                    "<td class=\"m_-1353841121882709300active-i\" style=\"line-height:1px;font-size:1px\"> <a style=\"text-decoration:none\" href=\"https://go.figma.com/Nzc4LU1FVS0yODEAAAGDPDHbK5QmziQek14jZBi_fBeEvau4p_JA1_OpUScJB-2SVYsLTjwwU7Jv9safFniY0FuTC0s=\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=https://go.figma.com/Nzc4LU1FVS0yODEAAAGDPDHbK5QmziQek14jZBi_fBeEvau4p_JA1_OpUScJB-2SVYsLTjwwU7Jv9safFniY0FuTC0s%3D&amp;source=gmail&amp;ust=1681573591757000&amp;usg=AOvVaw0y05a4Z1xWKJBusnCkGvWF\"><img src=\"https://ci4.googleusercontent.com/proxy/ntHGMsA2S0Glx3sbO76vLE6enme9OlftZgbNFSdjO513o30EXs6_F8POuLSEx7CD1ZuTIlcYFbXDtK1g3g_HrYmo-C-hI8g1K58pTpK3hpTdqd-54oNsyiDboMs=s0-d-e1-ft#https://static.figma.com/uploads/216212e7916d3148bdb7ed6dd87240106d782065\" width=\"30\" style=\"width:30px;max-width:30px;font-size:10px;line-height:12px;font-family:'Whyte',Arial,Helvetica,sans-serif;color:#000;background-color:#fff;vertical-align:top;text-align:center\" alt=\"twitter\" class=\"CToWUd\" data-bit=\"iit\"></a></td> \n" +
                    "<td width=\"30\" style=\"font-size:1px;line-height:1px\"></td> \n" +
                    "<td class=\"m_-1353841121882709300active-i\" style=\"line-height:1px;font-size:1px\"> <a style=\"text-decoration:none\" href=\"https://go.figma.com/Nzc4LU1FVS0yODEAAAGDPDHbK9uxl7vgp291M91TXQoycsXP_GJqfrMlFIXZfrs4DcXGDi5M5E9OlmWZUkzJ7-IhUR4=\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=https://go.figma.com/Nzc4LU1FVS0yODEAAAGDPDHbK9uxl7vgp291M91TXQoycsXP_GJqfrMlFIXZfrs4DcXGDi5M5E9OlmWZUkzJ7-IhUR4%3D&amp;source=gmail&amp;ust=1681573591757000&amp;usg=AOvVaw3I9KExSOVqh9Dav6sKGn0t\"><img src=\"https://ci5.googleusercontent.com/proxy/_9PqNNFde7Y5a5HCblCK5OZi34VifPL-gOJxdezAY-V3RyKEQsS-tz2DEKX0FToZ708DSWBk4d8MBCmKVm_DguQlJQpiHlHPkyHNQKxkUs1dxifgfBvaiSBa65c=s0-d-e1-ft#https://static.figma.com/uploads/af11eaaadaa6376b958648d273b63d7126441af9\" width=\"30\" style=\"width:30px;max-width:30px;font-size:10px;line-height:12px;font-family:'Whyte',Arial,Helvetica,sans-serif;color:#000;background-color:#fff;vertical-align:top;text-align:center\" alt=\"instagram\" class=\"CToWUd\" data-bit=\"iit\"></a></td> \n" +
                    "<td width=\"30\" style=\"font-size:1px;line-height:1px\"></td> \n" +
                    "<td class=\"m_-1353841121882709300active-i\" style=\"line-height:1px;font-size:1px\"> <a style=\"text-decoration:none\" href=\"https://go.figma.com/Nzc4LU1FVS0yODEAAAGDPDHbK_T_xs509gcBNyF0W1q_UzEKECOwszzmPlozHvql5TjhqDN41-kz7U89DYPSDmHWkhQ=\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=https://go.figma.com/Nzc4LU1FVS0yODEAAAGDPDHbK_T_xs509gcBNyF0W1q_UzEKECOwszzmPlozHvql5TjhqDN41-kz7U89DYPSDmHWkhQ%3D&amp;source=gmail&amp;ust=1681573591758000&amp;usg=AOvVaw04qh6P59lYD1i0mRQacFNe\"><img src=\"https://ci3.googleusercontent.com/proxy/eyh3ysMuTd11pZGuQ-da_R23VIVE0y2MYnN9z2FaE9tRzLvxyDmIRuaYraoBAwjzZJXzBUiwg3LUdX4m8X2oFEidwRY6F7qJZHANzXXJxj0-Naiv57tnNIYuShM=s0-d-e1-ft#https://static.figma.com/uploads/b75a7245bc497aaf501164d4cf61303096a261a5\" width=\"30\" style=\"width:30px;max-width:30px;font-size:10px;line-height:12px;font-family:'Whyte',Arial,Helvetica,sans-serif;color:#000;background-color:#fff;vertical-align:top;text-align:center\" alt=\"facebook\" class=\"CToWUd\" data-bit=\"iit\"></a></td> \n" +
                    "<td width=\"30\" style=\"font-size:1px;line-height:1px\"></td> \n" +
                    "<td class=\"m_-1353841121882709300active-i\" style=\"line-height:1px;font-size:1px\"> <a style=\"text-decoration:none\" href=\"https://go.figma.com/Nzc4LU1FVS0yODEAAAGDPDHbK1H653IFNgH6MlM1h8en8fQL3UZPL9e1TzqnU6jBlX_-OacwkzKnGUZ_UGG9R8_7eoc=\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=https://go.figma.com/Nzc4LU1FVS0yODEAAAGDPDHbK1H653IFNgH6MlM1h8en8fQL3UZPL9e1TzqnU6jBlX_-OacwkzKnGUZ_UGG9R8_7eoc%3D&amp;source=gmail&amp;ust=1681573591758000&amp;usg=AOvVaw3XY2rowHEQrRD36Hxpellp\"><img src=\"https://ci6.googleusercontent.com/proxy/jj83rKyiW92tzSWmkIV1WUeHrk3wequgiDDkYUXyXuggrxBqlaOvtrUZKDx83xl5v2wAN-SrF9rRs3ZJ0Huq_ufp63O2mTobiFZ_kJlOmsVhT6uIqnYebzQNaL8=s0-d-e1-ft#https://static.figma.com/uploads/3b79f70df0d97bdb6560e64b2a05d97f8af4b29a\" width=\"30\" style=\"width:30px;max-width:30px;font-size:10px;line-height:12px;font-family:'Whyte',Arial,Helvetica,sans-serif;color:#000;background-color:#fff;vertical-align:top;text-align:center\" alt=\"linkedin\" class=\"CToWUd\" data-bit=\"iit\"></a></td> \n" +
                    "<td width=\"30\" style=\"font-size:1px;line-height:1px\"></td> \n" +
                    "<td class=\"m_-1353841121882709300active-i\" style=\"line-height:1px;font-size:1px\"> <a style=\"text-decoration:none\" href=\"https://go.figma.com/Nzc4LU1FVS0yODEAAAGDPDHbK9KFObIvW4llpbsBQXGcbCRCLZIDg4KbixUcK4zMoobgEPU3iLq7WIxFyXX7BLbd50w=\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=https://go.figma.com/Nzc4LU1FVS0yODEAAAGDPDHbK9KFObIvW4llpbsBQXGcbCRCLZIDg4KbixUcK4zMoobgEPU3iLq7WIxFyXX7BLbd50w%3D&amp;source=gmail&amp;ust=1681573591758000&amp;usg=AOvVaw3bvJEer5bHDbNO3STX4_nk\"><img src=\"https://ci4.googleusercontent.com/proxy/5-1Gg5d_RAK4P7x8Y4d96Xlg5OHDODEvWSwNBKyzIPJeTSs30aJWofccsm0tw3IJIERymyo7J5yJZ-bVCZkpIFVCrH3xWlsBe5m0qx3yTrhTw5Al0Uq7rxDFK-o=s0-d-e1-ft#https://static.figma.com/uploads/8e1a4619a94fa5e42914cfcd974e4a3bd5e6d39c\" width=\"30\" style=\"width:30px;max-width:30px;font-size:10px;line-height:12px;font-family:'Whyte',Arial,Helvetica,sans-serif;color:#000;background-color:#fff;vertical-align:top;text-align:center\" alt=\"dribble\" class=\"CToWUd\" data-bit=\"iit\"></a></td> \n" +
                    "</tr> \n" +
                    "</tbody> \n" +
                    "</table> </td> \n" +
                    "</tr> \n" +
                    "<tr> \n" +
                    "<td align=\"center\" style=\"font-size:12px;line-height:17px;font-family:'Whyte',Arial,Helvetica,sans-serif;color:#a5a5a5;padding:0 0 32px\">760 Market Street, Floor 10<br> San Francisco, CA, 94102</td> \n" +
                    "</tr> \n" +
                    "</tbody> \n" +
                    "</table>");
            // Creating a simple mail message
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            // Setting up necessary details
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            messageHelper.setFrom(sender);
            messageHelper.setTo(recipient);
            messageHelper.setSubject(type.equals(CommonConstant.RESET_TYPE) ? CommonConstant.SUBJECT_RESET : CommonConstant.RESET_TYPE);
            messageHelper.setText(content.toString(), true);
            javaMailSender.send(mimeMessage);

            // Sending the mail
//            javaMailSender.send(mailMessage);
            return verifyCode;
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            System.out.println(e);
            return "error";
        }
    }

    public String sendSimpleMailWithPassword(String recipient) {
        // Try block to check for exceptions
        try {
            String verifyCode = CommonUtils.randomString();
            String password = CommonUtils.randomString(6);
            StringBuilder content = new StringBuilder();
            content.append("<table align=\"center\" class=\"m_-1353841121882709300w-100p\" width=\"476\" style=\"max-width:476px;margin:0 auto\" cellpadding=\"0\" cellspacing=\"0\"> \n" +
                    "<tbody> \n" +
                    "<tr> \n" +
                    "<td align=\"center\" style=\"font-weight:bold;font-size:20px;line-height:32px;font-family:'Whyte',Arial,Helvetica,sans-serif;color:#000;padding:10px 0 32px;text-align:center\"> <h1 style=\"font-weight:bold;font-size:24px;line-height:32px;font-family:'Whyte',Arial,Helvetica,sans-serif;color:#000;text-align:center\">Welcome to FPT Learning Material</h1> </td> \n" +
                    "</tr> \n" +
                    "<tr> \n" +
                    "<td align=\"center\" style=\"color:#000;font-size:18px;line-height:26px;font-family:'Whyte',Arial,Helvetica,sans-serif;text-align:left\"> <p style=\"font-size:18px;line-height:26px;font-family:'Whyte',Helvetica,Arial,sans-serif;color:#000000;font-weight:normal;margin:0\">");
            content.append("Here's your verification code: ");
            content.append(
                    "</p></td> \n" +
                            "</tr> \n" +
                            "<tr> \n" +
                            "<td class=\"m_-1353841121882709300pb-60\" align=\"center\" style=\"padding:50px 0 60px\"> \n" +
                            "<table align=\"center\" style=\"margin:0 auto\" cellpadding=\"0\" cellspacing=\"0\"> \n" +
                            "<tbody> \n" +
                            "<tr> \n" +
                            "<td class=\"m_-1353841121882709300active-t\" align=\"center\" style=\"background-color:#ffffff;font-weight:bold;font-size:20px;line-height:22px;font-family:'Whyte',Helvetica,Arial,sans-serif;border:3px solid #000000;border-radius:5px;padding: 20px 50px\">\n");
            content.append(verifyCode);

            content.append("</td></tr><tr><td align=\"center\" style=\"color:#000;font-size:18px;line-height:26px;font-family:'Whyte',Arial,Helvetica,sans-serif;text-align:left\"> <p style=\"font-size:18px;line-height:26px;font-family:'Whyte',Helvetica,Arial,sans-serif;color:#000000;font-weight:normal;margin:50px 0 0 0\">");
            content.append("Here's your password: ");
            content.append(
                    "</p></td> \n" +
                            "</tr> \n" +
                            "<tr> \n" +
                            "<td class=\"m_-1353841121882709300pb-60\" align=\"center\" style=\"padding:50px 0 60px\"> \n" +
                            "<table align=\"center\" style=\"margin:0 auto\" cellpadding=\"0\" cellspacing=\"0\"> \n" +
                            "<tbody> \n" +
                            "<tr> \n" +
                            "<td class=\"m_-1353841121882709300active-t\" align=\"center\" style=\"background-color:#ffffff;font-weight:bold;font-size:20px;line-height:22px;font-family:'Whyte',Helvetica,Arial,sans-serif;border:3px solid #000000;border-radius:5px;padding: 20px 50px\">\n");
            content.append(password);

            content.append("</td></tr> \n" +
                    "</tbody> \n" +
                    "</table> </td> \n" +
                    "</tr> \n" +
                    " \n" +
                    "<tr> \n" +
                    "<td> \n" +
                    "<table class=\"m_-1353841121882709300w-100p\" width=\"540\" align=\"center\" style=\"max-width:540px;margin:0 auto\" cellpadding=\"0\" cellspacing=\"0\"> \n" +
                    "<tbody> \n" +
                    "<tr> \n" +
                    "<td class=\"m_-1353841121882709300pt-60\" align=\"center\" style=\"border-top:1px solid #dedede;color:#a5a5a5;font-size:14px;line-height:18px;font-family:'Whyte',Arial,Helvetica,sans-serif;text-align:center;padding:60px 24px 32px\">FPT Learning Material</td> \n" +
                    "</tr> \n" +
                    "</tbody> \n" +
                    "</table> </td> \n" +
                    "</tr> \n" +
                    "<tr> \n" +
                    "<td align=\"center\" style=\"padding:10px 24px 46px\"> \n" +
                    "<table width=\"300\" style=\"max-width:300px;margin:0 auto\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\"> \n" +
                    "<tbody> \n" +
                    "<tr> \n" +
                    "<td class=\"m_-1353841121882709300active-i\" style=\"line-height:1px;font-size:1px\"> <a style=\"text-decoration:none\" href=\"https://go.figma.com/Nzc4LU1FVS0yODEAAAGDPDHbKwP3FjWoZlD8r70XWj-FSM2MnkC3_Yw3neOzjfsRzj38DDaMF5q5l-fMkOefkCY5FIk=\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=https://go.figma.com/Nzc4LU1FVS0yODEAAAGDPDHbKwP3FjWoZlD8r70XWj-FSM2MnkC3_Yw3neOzjfsRzj38DDaMF5q5l-fMkOefkCY5FIk%3D&amp;source=gmail&amp;ust=1681573591757000&amp;usg=AOvVaw0JCGz_ZBkpZfC8U5l5lKOh\"><img src=\"https://ci6.googleusercontent.com/proxy/6yF-wMiPnkSHk8Pr0YrYguIcQXiR51LtC16Lfunic4SwtE5JYf5_2nkUHHmNryVQc_6uMGIb9GFgrxWRl1v78qAeIArHcFOwykd3V7vCkPe-hY9Gysy3Z-_a0g0=s0-d-e1-ft#https://static.figma.com/uploads/362dad2073b1e974b85e78019adb0badc946df74\" width=\"30\" style=\"width:30px;max-width:30px;font-size:10px;line-height:12px;font-family:'Whyte',Arial,Helvetica,sans-serif;color:#000;background-color:#fff;vertical-align:top;text-align:center\" alt=\"figma\" class=\"CToWUd\" data-bit=\"iit\"></a></td> \n" +
                    "<td width=\"30\" style=\"font-size:1px;line-height:1px\"></td> \n" +
                    "<td class=\"m_-1353841121882709300active-i\" style=\"line-height:1px;font-size:1px\"> <a style=\"text-decoration:none\" href=\"https://go.figma.com/Nzc4LU1FVS0yODEAAAGDPDHbK5QmziQek14jZBi_fBeEvau4p_JA1_OpUScJB-2SVYsLTjwwU7Jv9safFniY0FuTC0s=\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=https://go.figma.com/Nzc4LU1FVS0yODEAAAGDPDHbK5QmziQek14jZBi_fBeEvau4p_JA1_OpUScJB-2SVYsLTjwwU7Jv9safFniY0FuTC0s%3D&amp;source=gmail&amp;ust=1681573591757000&amp;usg=AOvVaw0y05a4Z1xWKJBusnCkGvWF\"><img src=\"https://ci4.googleusercontent.com/proxy/ntHGMsA2S0Glx3sbO76vLE6enme9OlftZgbNFSdjO513o30EXs6_F8POuLSEx7CD1ZuTIlcYFbXDtK1g3g_HrYmo-C-hI8g1K58pTpK3hpTdqd-54oNsyiDboMs=s0-d-e1-ft#https://static.figma.com/uploads/216212e7916d3148bdb7ed6dd87240106d782065\" width=\"30\" style=\"width:30px;max-width:30px;font-size:10px;line-height:12px;font-family:'Whyte',Arial,Helvetica,sans-serif;color:#000;background-color:#fff;vertical-align:top;text-align:center\" alt=\"twitter\" class=\"CToWUd\" data-bit=\"iit\"></a></td> \n" +
                    "<td width=\"30\" style=\"font-size:1px;line-height:1px\"></td> \n" +
                    "<td class=\"m_-1353841121882709300active-i\" style=\"line-height:1px;font-size:1px\"> <a style=\"text-decoration:none\" href=\"https://go.figma.com/Nzc4LU1FVS0yODEAAAGDPDHbK9uxl7vgp291M91TXQoycsXP_GJqfrMlFIXZfrs4DcXGDi5M5E9OlmWZUkzJ7-IhUR4=\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=https://go.figma.com/Nzc4LU1FVS0yODEAAAGDPDHbK9uxl7vgp291M91TXQoycsXP_GJqfrMlFIXZfrs4DcXGDi5M5E9OlmWZUkzJ7-IhUR4%3D&amp;source=gmail&amp;ust=1681573591757000&amp;usg=AOvVaw3I9KExSOVqh9Dav6sKGn0t\"><img src=\"https://ci5.googleusercontent.com/proxy/_9PqNNFde7Y5a5HCblCK5OZi34VifPL-gOJxdezAY-V3RyKEQsS-tz2DEKX0FToZ708DSWBk4d8MBCmKVm_DguQlJQpiHlHPkyHNQKxkUs1dxifgfBvaiSBa65c=s0-d-e1-ft#https://static.figma.com/uploads/af11eaaadaa6376b958648d273b63d7126441af9\" width=\"30\" style=\"width:30px;max-width:30px;font-size:10px;line-height:12px;font-family:'Whyte',Arial,Helvetica,sans-serif;color:#000;background-color:#fff;vertical-align:top;text-align:center\" alt=\"instagram\" class=\"CToWUd\" data-bit=\"iit\"></a></td> \n" +
                    "<td width=\"30\" style=\"font-size:1px;line-height:1px\"></td> \n" +
                    "<td class=\"m_-1353841121882709300active-i\" style=\"line-height:1px;font-size:1px\"> <a style=\"text-decoration:none\" href=\"https://go.figma.com/Nzc4LU1FVS0yODEAAAGDPDHbK_T_xs509gcBNyF0W1q_UzEKECOwszzmPlozHvql5TjhqDN41-kz7U89DYPSDmHWkhQ=\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=https://go.figma.com/Nzc4LU1FVS0yODEAAAGDPDHbK_T_xs509gcBNyF0W1q_UzEKECOwszzmPlozHvql5TjhqDN41-kz7U89DYPSDmHWkhQ%3D&amp;source=gmail&amp;ust=1681573591758000&amp;usg=AOvVaw04qh6P59lYD1i0mRQacFNe\"><img src=\"https://ci3.googleusercontent.com/proxy/eyh3ysMuTd11pZGuQ-da_R23VIVE0y2MYnN9z2FaE9tRzLvxyDmIRuaYraoBAwjzZJXzBUiwg3LUdX4m8X2oFEidwRY6F7qJZHANzXXJxj0-Naiv57tnNIYuShM=s0-d-e1-ft#https://static.figma.com/uploads/b75a7245bc497aaf501164d4cf61303096a261a5\" width=\"30\" style=\"width:30px;max-width:30px;font-size:10px;line-height:12px;font-family:'Whyte',Arial,Helvetica,sans-serif;color:#000;background-color:#fff;vertical-align:top;text-align:center\" alt=\"facebook\" class=\"CToWUd\" data-bit=\"iit\"></a></td> \n" +
                    "<td width=\"30\" style=\"font-size:1px;line-height:1px\"></td> \n" +
                    "<td class=\"m_-1353841121882709300active-i\" style=\"line-height:1px;font-size:1px\"> <a style=\"text-decoration:none\" href=\"https://go.figma.com/Nzc4LU1FVS0yODEAAAGDPDHbK1H653IFNgH6MlM1h8en8fQL3UZPL9e1TzqnU6jBlX_-OacwkzKnGUZ_UGG9R8_7eoc=\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=https://go.figma.com/Nzc4LU1FVS0yODEAAAGDPDHbK1H653IFNgH6MlM1h8en8fQL3UZPL9e1TzqnU6jBlX_-OacwkzKnGUZ_UGG9R8_7eoc%3D&amp;source=gmail&amp;ust=1681573591758000&amp;usg=AOvVaw3XY2rowHEQrRD36Hxpellp\"><img src=\"https://ci6.googleusercontent.com/proxy/jj83rKyiW92tzSWmkIV1WUeHrk3wequgiDDkYUXyXuggrxBqlaOvtrUZKDx83xl5v2wAN-SrF9rRs3ZJ0Huq_ufp63O2mTobiFZ_kJlOmsVhT6uIqnYebzQNaL8=s0-d-e1-ft#https://static.figma.com/uploads/3b79f70df0d97bdb6560e64b2a05d97f8af4b29a\" width=\"30\" style=\"width:30px;max-width:30px;font-size:10px;line-height:12px;font-family:'Whyte',Arial,Helvetica,sans-serif;color:#000;background-color:#fff;vertical-align:top;text-align:center\" alt=\"linkedin\" class=\"CToWUd\" data-bit=\"iit\"></a></td> \n" +
                    "<td width=\"30\" style=\"font-size:1px;line-height:1px\"></td> \n" +
                    "<td class=\"m_-1353841121882709300active-i\" style=\"line-height:1px;font-size:1px\"> <a style=\"text-decoration:none\" href=\"https://go.figma.com/Nzc4LU1FVS0yODEAAAGDPDHbK9KFObIvW4llpbsBQXGcbCRCLZIDg4KbixUcK4zMoobgEPU3iLq7WIxFyXX7BLbd50w=\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=https://go.figma.com/Nzc4LU1FVS0yODEAAAGDPDHbK9KFObIvW4llpbsBQXGcbCRCLZIDg4KbixUcK4zMoobgEPU3iLq7WIxFyXX7BLbd50w%3D&amp;source=gmail&amp;ust=1681573591758000&amp;usg=AOvVaw3bvJEer5bHDbNO3STX4_nk\"><img src=\"https://ci4.googleusercontent.com/proxy/5-1Gg5d_RAK4P7x8Y4d96Xlg5OHDODEvWSwNBKyzIPJeTSs30aJWofccsm0tw3IJIERymyo7J5yJZ-bVCZkpIFVCrH3xWlsBe5m0qx3yTrhTw5Al0Uq7rxDFK-o=s0-d-e1-ft#https://static.figma.com/uploads/8e1a4619a94fa5e42914cfcd974e4a3bd5e6d39c\" width=\"30\" style=\"width:30px;max-width:30px;font-size:10px;line-height:12px;font-family:'Whyte',Arial,Helvetica,sans-serif;color:#000;background-color:#fff;vertical-align:top;text-align:center\" alt=\"dribble\" class=\"CToWUd\" data-bit=\"iit\"></a></td> \n" +
                    "</tr> \n" +
                    "</tbody> \n" +
                    "</table> </td> \n" +
                    "</tr> \n" +
                    "<tr> \n" +
                    "<td align=\"center\" style=\"font-size:12px;line-height:17px;font-family:'Whyte',Arial,Helvetica,sans-serif;color:#a5a5a5;padding:0 0 32px\">760 Market Street, Floor 10<br> San Francisco, CA, 94102</td> \n" +
                    "</tr> \n" +
                    "</tbody> \n" +
                    "</table>");
            // Creating a simple mail message
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            // Setting up necessary details
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            messageHelper.setFrom(sender);
            messageHelper.setTo(recipient);
            messageHelper.setSubject("[FLM-G2] Create account");
            messageHelper.setText(content.toString(), true);
            javaMailSender.send(mimeMessage);

            // Sending the mail
//            javaMailSender.send(mailMessage);
            return verifyCode + "-" + password;
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            System.out.println(e);
            return "error";
        }
    }


    // Method 2
    // To send an email with attachment
    public String
    sendMailWithAttachment(EmailDetails details) {
        // Creating a mime message
        MimeMessage mimeMessage
                = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {

            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(
                    details.getSubject());

            // Adding the attachment
            FileSystemResource file
                    = new FileSystemResource(
                    new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(
                    file.getFilename(), file);

            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }

        // Catch block to handle MessagingException
        catch (MessagingException e) {

            // Display message when exception occurred
            return "Error while sending mail!!!";
        }
    }
}
