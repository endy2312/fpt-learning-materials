package com.fpt.g2.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserRequestDTO {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String mobile;
    private String password;
    private String verifyCode;
    private String image;
    private MultipartFile imageUpload;
    private String signupType;
    private String title;
    private String jobTitle;
    private String organization;
    private List<Long> userRoleIds;
    private boolean deleteFlag;
}
