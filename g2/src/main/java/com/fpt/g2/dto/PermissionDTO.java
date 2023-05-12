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
public class PermissionDTO extends RequestCommonDTO{
    private String role;

    public String getRole() {
        return Objects.isNull(role) || role.equals("All") || role.isEmpty() ? null : role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
