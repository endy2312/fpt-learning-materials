package com.fpt.g2.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String mobile;
    private String password;
    private String image;
    private String signupType;
    private boolean deleteFlag;
}
