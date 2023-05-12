package com.fpt.g2.service;

import com.fpt.g2.dto.RequestCommonDTO;
import com.fpt.g2.entity.User;
import org.springframework.data.domain.Page;

public interface AdminService {

    Page getAllUser(RequestCommonDTO requestCommonDTO, String status, String role);

    User getUserDetails(Long id);
}
