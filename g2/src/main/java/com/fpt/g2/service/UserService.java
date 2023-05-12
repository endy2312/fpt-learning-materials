package com.fpt.g2.service;

import com.fpt.g2.dto.EmailRegisterRequestDTO;
import com.fpt.g2.dto.PhoneRegisterRequestDTO;
import com.fpt.g2.dto.UserRequestDTO;
import com.fpt.g2.entity.User;

import java.util.List;


public interface UserService {
    User getUserByUsername(String username);
    Boolean isUserEmailExisted(String email);
    Boolean isUserPhoneExisted(String email);
    void registerUserByEmail(EmailRegisterRequestDTO registerUser);
    void registerUserByPhone(PhoneRegisterRequestDTO registerUser);
    User findUser(String email,String password);

    User findById(Long id);

    User saveOrUpdate(UserRequestDTO form, String updateFrom);

    String lockOrUnlockAccount(Long id);

    void resetPassword(String username,String password);

    void updateUserRole(List<Long> roleIds, Long userId);

    List<User> getAllStaff();

    List<User> getAllDesigner();

    List<User> getAllReviewer();
}
