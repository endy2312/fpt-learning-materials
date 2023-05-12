package com.fpt.g2.dto;

import com.fpt.g2.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionsRqDTO {
    public List<Permission> permissionRq;
}
