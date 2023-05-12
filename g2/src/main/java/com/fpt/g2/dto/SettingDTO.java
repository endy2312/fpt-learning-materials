package com.fpt.g2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SettingDTO extends RequestCommonDTO{

    private String status;
    private String type;

    public String getType() {
        return Objects.isNull(type) || type.equals("All") || type.isEmpty() ? null : type;
    }

    public void setType(String type) {
        this.type = type;
    }
}