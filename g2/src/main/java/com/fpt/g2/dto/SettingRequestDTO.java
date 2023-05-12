package com.fpt.g2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SettingRequestDTO {
    private String title;
    private String type;
    private String value;
    private Integer displayOrder;
    private String status;
    private String description;
}
