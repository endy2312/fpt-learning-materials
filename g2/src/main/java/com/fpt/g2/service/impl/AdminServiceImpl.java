package com.fpt.g2.service.impl;

import com.fpt.g2.dto.RequestCommonDTO;
import com.fpt.g2.entity.User;
import com.fpt.g2.repository.UserRepository;
import com.fpt.g2.service.AdminService;
import com.fpt.g2.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page getAllUser(RequestCommonDTO request, String status, String role) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), request.getSortBy());
        String keyword = request.getKeyword();
        if (!CommonUtils.isNullOrEmpty(request.getKeyword())) {
            keyword = "%" + keyword + "%";
        }

        Boolean statusCheck = null;
        if ( status != null) {
            statusCheck = !status.equalsIgnoreCase("Activate");
        }
        
        Page<User> users = userRepository.findAll(keyword, statusCheck, pageable);

        return users;
    }

    @Override
    public User getUserDetails(Long id) {
        User user = userRepository.findById(id).get();
        return user;
    }
}
