package com.fpt.g2.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UploadDTO {
    private MultipartFile file;
}
