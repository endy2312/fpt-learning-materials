package com.fpt.g2.utils;

import com.fpt.g2.constant.CommonConstant;
import com.fpt.g2.dto.UploadDTO;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {
    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
    public static SecretKeySpec secretKey;

    public static byte[] key;
    public static String ALGORITHM = "AES";
    public static Timestamp resultTimestamp() {
        LocalDateTime localDateTimeJST = Instant.now().atOffset(ZoneOffset.of("+07:00")).toLocalDateTime();
        return Timestamp.valueOf(localDateTimeJST);
    }
    public static String randomString(){
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(5);
        for(int i = 0; i < 5; i++)
            sb.append(CommonConstant.randomString.charAt(rnd.nextInt(CommonConstant.randomString.length())));
        return sb.toString();
    }

    public static String randomString(Integer length){
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for(int i = 0; i < length; i++)
            sb.append(CommonConstant.randomString.charAt(rnd.nextInt(CommonConstant.randomString.length())));
        return sb.toString();
    }

    public static String decrypt(String strToDecrypt, String secret) {
        try {
            prepareSecreteKey(secret);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            logger.info("Error while decrypting: {}", e.getMessage());
        }
        return null;
    }

    public static void prepareSecreteKey(String myKey) {
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest();
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.getStackTrace();
        }
    }

    private static Pattern _emailAddressPattern = Pattern.compile(
            "(?i)^((([a-z]|\\d|[!#$%&'*+\\-/=?^_`{|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+(\\.([a-z]|\\d|[!#$%&'*+\\-/=?^_`{|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?$");
    private static Pattern _passwordPattern = Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[~!@#$%^&*]).{8,}$");

    private static Pattern _usernamePattern = Pattern.compile(
            "^[a-zA-Z0-9._]+$");

    private static Pattern _phoneNumberPattern = Pattern.compile("^0\\d{9}$");

    public static boolean isNullOrEmpty(String str) {
        return (str == null || str.trim().isEmpty());
    }

    public static boolean isNullOrEmpty(List data) {
        return (data == null || data.isEmpty());
    }


    public static Date convertStringToDate(String date) throws Exception {
        if (date == null || date.trim().isEmpty()) {
            return null;
        } else {
            String pattern = "dd/MM/yyyy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            dateFormat.setLenient(false);
            return dateFormat.parse(date);
        }
    }


    public static String convertDateToString(Date date) {
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return dateFormat.format(date);
        }
    }


    public static String convertDateToString(Date date, String pattern) {
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            return dateFormat.format(date);
        }
    }

    public static Object NVL(Object value, Object defaultValue) {
        return value == null ? defaultValue : value;
    }

    public static Integer NVL(Integer value, Integer defaultValue) {
        return value == null ? defaultValue : value;
    }

    public static BigDecimal NVL(BigDecimal value) {
        return value == null ? new BigDecimal(0) : value;
    }

    public static Double NVL(Double value, Double defaultValue) {
        return value == null ? defaultValue : value;
    }

    public static Long NVL(Long value, Long defaultValue) {
        return value == null ? defaultValue : value;
    }

    public static String NVL(String value, String nullValue, String notNullValue) {

        return value == null ? nullValue : notNullValue.trim();
    }

    public static String NVL(String value, String defaultValue) {

        return NVL(value, defaultValue, value);
    }

    public static String NVL(String value) {

        return NVL(value, "");
    }

    public static Long NVL(Long value) {

        return NVL(value, 0L);
    }

    public static void filter(String str, StringBuilder queryString, List<Object> paramList, String field) {
        if ((str != null) && !"".equals(str.trim())) {
            queryString.append(" AND LOWER(").append(field).append(") LIKE ? ESCAPE '/'");
            str =
                    str = "%" + str.trim().toLowerCase().replace("/", "//").replace("_", "/_").replace("%", "/%") + "%";
            paramList.add(str);
        }
    }

    public static void filter(Long n, StringBuilder queryString, List<Object> paramList, String field) {
        if ((n != null) && (n > 0L)) {
            queryString.append(" AND ").append(field).append(" = ? ");
            paramList.add(n);
        }
    }

    public static void filter(Date date, StringBuilder queryString, List<Object> paramList, String field) {
        if ((date != null)) {
            queryString.append(" AND ").append(field).append(" = ? ");
            paramList.add(date);
        }
    }

    public static String formatNumber(Double d) {
        if (d == null) {
            return "";
        } else {
            DecimalFormat format = new DecimalFormat("######.#####");
            return format.format(d);
        }
    }

    public static String formatNumber(Long d) {
        if (d == null) {
            return "";
        } else {
            DecimalFormat format = new DecimalFormat("######");
            return format.format(d);
        }
    }

    public static List<Long> string2ListLong(String inpuString, String separator) {
        List<Long> outPutList = new ArrayList<Long>();

        if (inpuString != null && !"".equals(inpuString.trim()) && separator != null && !"".equals(separator.trim())) {
            String[] idArr = inpuString.split(separator);
            for (String idArr1 : idArr) {
                if (idArr1 != null && !"".equals(idArr1.trim())) {
                    outPutList.add(Long.parseLong(idArr1.trim()));
                }
            }
        }

        return outPutList;
    }

    private static boolean isCollectionEmpty(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof String) {
            if (((String) object).trim().length() == 0) {
                return true;
            }
        } else if (object instanceof Collection) {
            return isCollectionEmpty((Collection<?>) object);
        }
        return false;
    }

    public static boolean isEmailAddress(String emailAddress) {
        if (isNullOrEmpty(emailAddress)) {
            return false;
        }
        Matcher matcher = _emailAddressPattern.matcher(emailAddress);
        return matcher.matches();
    }

    public static boolean isUserName(String password) {
        if (isNullOrEmpty(password)) {
            return false;
        }
        Matcher matcher = _usernamePattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isPassword(String password) {
        if (isNullOrEmpty(password)) {
            return false;
        }
        Matcher matcher = _passwordPattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isPhoneNumber(String phoneNumber) {
        if (isNullOrEmpty(phoneNumber)) {
            return false;
        }
        Matcher matcher = _phoneNumberPattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public static String getWhiteListResource(String key) {
        try {
            ResourceBundle rb = ResourceBundle.getBundle("whiteList");
            return rb.getString(key);
        } catch (Exception ex) {
            logger.error("getApplicationResource:", ex);
            return "";
        }
    }

    public static String toNonAccentVietnamese(String str) {
        str = str.replaceAll("[AÁÀÃẠÂẤẦẪẬĂẮẰẴẶ]", "A");
        str = str.replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a");
        str = str.replaceAll("[EÉÈẼẸÊẾỀỄỆ]", "E");
        str = str.replaceAll("[èéẹẻẽêềếệểễ]", "e");
        str = str.replaceAll("[IÍÌĨỊ]", "I");
        str = str.replaceAll("[ìíịỉĩ]", "i");
        str = str.replaceAll("[OÓÒÕỌÔỐỒỖỘƠỚỜỠỢ]", "O");
        str = str.replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o");
        str = str.replaceAll("[UÚÙŨỤƯỨỪỮỰ]", "U");
        str = str.replaceAll("[ùúụủũưừứựửữ]", "u");
        str = str.replaceAll("[YÝỲỸỴ]", "Y");
        str = str.replaceAll("[ỳýỵỷỹ]", "y");
        str = str.replaceAll("Đ", "D");
        str = str.replaceAll("đ", "d");
        str = str.replaceAll("[̣̀́̃̉]", ""); // Huyền sắc hỏi ngã nặng
        str = str.replaceAll("[ˆ̛̆]", ""); // Â, Ê, Ă, Ơ, Ư
        return str;
    }
    public static String MysqlRealScapeString(String str){
        String data = null;
        if (str != null && str.length() > 0) {
            str = str.replace("\\", "\\\\");
            str = str.replace("'", "\\'");
            str = str.replace("\0", "\\0");
            str = str.replace("\n", "\\n");
            str = str.replace("\r", "\\r");
            str = str.replace("\"", "\\\"");
            str = str.replace("\\x1a", "\\Z");
            data = str;
        }
        return data;
    }

    public static String getEscapeKeyword(String keyword){
        if(keyword != null && !keyword.isEmpty()){
            return  "%" + MysqlRealScapeString(keyword) + "%";
        }
        return "%%";
    }

    public static File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    public static String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
    }

    public static String uploadFile(MultipartFile multipartFile) throws IOException {
        String objectName = CommonUtils.generateFileName(multipartFile);
        ClassLoader classLoader = CommonUtils.class.getClassLoader();
        InputStream serviceAccount = classLoader.getResourceAsStream("firebase_sdk.json");
        File file = CommonUtils.convertMultiPartToFile(multipartFile);
        Path filePath = file.toPath();

        Storage storage = StorageOptions.newBuilder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).setProjectId(CommonConstant.FIREBASE_PROJECT_ID).build().getService();
        BlobId blobId = BlobId.of(CommonConstant.FIREBASE_BUCKET, objectName);
        String token = CommonUtils.randomString(32);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setMetadata(Map.of("firebaseStorageDownloadTokens", token)).build();
        storage.create(blobInfo, Files.readAllBytes(filePath));
        file.delete();
        String urlImage = "https://firebasestorage.googleapis.com/v0/b/flm-fpt-7b970.appspot.com/o/" + objectName + "?alt=media&token=" + token;
        System.out.println(urlImage); // đây là url ảnh
        return urlImage;
    }

}
