package com.fpt.g2.service.impl;

import com.fpt.g2.service.SmsService;
import com.fpt.g2.utils.CommonUtils;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

@Service
public class SmsServiceImpl implements SmsService {
    @Value("${esms.api.key}")
    String APIKey;
    @Value("${esms.api.secret.key}")
    String secretKey;

    @Override
    public String sendSMS(String phone) throws UnsupportedEncodingException {
        String randomCode = CommonUtils.randomString();

            String ct = randomCode + " la ma xac minh dang ky Baotrixemay cua ban";

            String url = "http://rest.esms.vn/MainService.svc/json/SendMultipleMessage_V4_get?ApiKey="
                    + URLEncoder.encode(APIKey, "UTF-8")
                    + "&SecretKey=" + URLEncoder.encode(secretKey, "UTF-8")
                    + "&SmsType=2&Brandname=" + URLEncoder.encode("Baotrixemay", "UTF-8")
                    + "&Phone=" + URLEncoder.encode(phone, "UTF-8")
                    + "&Content=" + URLEncoder.encode(ct, "UTF-8");

            URL obj;
            try {
                obj = new URL(url);

                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                //you need to encode ONLY the values of the parameters

                con.setRequestMethod("GET");
                con.setRequestProperty("Accept", "application/json");

                int responseCode = con.getResponseCode();
                if (responseCode == 200)//Đã gọi URL thành công, tuy nhiên bạn phải tự kiểm tra CodeResult xem tin nhắn có gửi thành công không, vì có thể tài khoản bạn không đủ tiền thì sẽ thất bại
                {
                    //Check CodeResult from response
                }
                //Đọc Response
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print result
                JSONObject json = (JSONObject) new JSONParser().parse(response.toString());
                System.out.println("CodeResult=" + json.get("CodeResult"));
                System.out.println("SMSID=" + json.get("SMSID"));
                System.out.println("ErrorMessage=" + json.get("ErrorMessage"));
                //document.getElementsByTagName("CountRegenerate").item(0).va
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return randomCode;
    }
}
