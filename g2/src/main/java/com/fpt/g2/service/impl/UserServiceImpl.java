package com.fpt.g2.service.impl;

import com.fpt.g2.constant.CommonConstant;
import com.fpt.g2.dto.EmailRegisterRequestDTO;
import com.fpt.g2.dto.PhoneRegisterRequestDTO;
import com.fpt.g2.dto.UserRequestDTO;
import com.fpt.g2.entity.Setting;
import com.fpt.g2.entity.User;
import com.fpt.g2.entity.UserRole;
import com.fpt.g2.repository.SettingRepository;
import com.fpt.g2.repository.UserRepository;
import com.fpt.g2.repository.UserRoleRepository;
import com.fpt.g2.service.SettingService;
import com.fpt.g2.service.UserService;
import com.fpt.g2.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private SettingService settingService;


    @Override
    public User getUserByUsername(String username) {
        User user = userRepository.findUserByUsernameAndDeleteFlagIsFalse(username);
        return user;
    }

    @Override
    public Boolean isUserEmailExisted(String email) {
        User userByEmail = userRepository.findUserByEmail(email);
        if (Objects.isNull(userByEmail)) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean isUserPhoneExisted(String mobile) {
        User userByMobile = userRepository.findUserByMobile(mobile);
        if (Objects.isNull(userByMobile)) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public void registerUserByEmail(EmailRegisterRequestDTO registerUser) {
        User user = new User();
        user.setFullName(registerUser.getFullName());
        user.setUsername(registerUser.getUsername().trim());
        user.setEmail(registerUser.getEmail().trim());
        user.setPassword(passwordEncoder.encode(registerUser.getPassword()));
        user.setSignupType(CommonConstant.SIGNUP_EMAIL);
        userRepository.saveAndFlush(user);

        Setting setting = settingRepository.findByTypeAndTitle(CommonConstant.SETTING_TYPE_ROLE, CommonConstant.ROLE_STUDENT);

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setSetting(setting);
        userRole.setActive(true);
        userRoleRepository.save(userRole);
    }

    @Override
    public void registerUserByPhone(PhoneRegisterRequestDTO registerUser) {
        User user = new User();
        user.setFullName(registerUser.getFullName());
        user.setUsername(registerUser.getUsername().trim());
        user.setMobile(registerUser.getPhone().trim());
        user.setPassword(passwordEncoder.encode(registerUser.getPassword()));
        user.setSignupType(CommonConstant.SIGNUP_MOBILE);
        userRepository.saveAndFlush(user);

        Setting setting = settingRepository.findByTypeAndTitle(CommonConstant.SETTING_TYPE_ROLE, CommonConstant.ROLE_STUDENT);

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setSetting(setting);
        userRole.setActive(true);
        userRoleRepository.save(userRole);
    }

    @Override
    public User findUser(String email, String password) {
        return userRepository.findByEmailOrUsernameOrMobileAndPassword(email, passwordEncoder.encode(password));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    @Transactional
    public User saveOrUpdate(UserRequestDTO form, String updateFrom) {
        User user;
        if (form.getId() != null) {
            user = userRepository.findById(form.getId()).get();
        } else {
            user = new User();
            if (!CommonUtils.isNullOrEmpty(form.getPassword())) {
                user.setPassword(passwordEncoder.encode(form.getPassword()));
            }
        }

        if (user != null) {
            if (user.isDeleteFlag() == true) {
                form.setDeleteFlag(true);
            }

            user.setFullName(form.getFullName());
            user.setUsername(form.getUsername());

            if (!CommonUtils.isNullOrEmpty(form.getEmail())) {
                user.setEmail(form.getEmail());
            }
            if (form.getSignupType().equalsIgnoreCase("email") || form.getSignupType().equalsIgnoreCase("google")) {
                user.setMobile(form.getMobile());
            }

            if (!Objects.isNull(form.getImageUpload())) {
                try {
                    user.setImage(CommonUtils.uploadFile(form.getImageUpload()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            if (!CommonUtils.isNullOrEmpty(form.getSignupType()) && CommonUtils.isNullOrEmpty(user.getSignupType())) {
                user.setSignupType(form.getSignupType());
            }
            if (!CommonUtils.isNullOrEmpty(form.getTitle())) {
                user.setTitle(form.getTitle());
            }
            if (!CommonUtils.isNullOrEmpty(form.getJobTitle())) {
                user.setJobTitle(form.getJobTitle());
            }
            if (!CommonUtils.isNullOrEmpty(form.getOrganization())) {
                user.setOrganization(form.getOrganization());
            }
            user.setUpdatedBy(user.getId());
        }

        user = userRepository.save(user);
        if (updateFrom.equalsIgnoreCase("admin")) {
            updateUserRole(form.getUserRoleIds(), user.getId());
        }
        return user;
    }

    @Override
    public String lockOrUnlockAccount(Long id) {
        User user;
        if (id != null) {
            user = userRepository.findById(id).get();
        } else {
            return CommonConstant.RECORD_DELETED;
        }
        if (user.isDeleteFlag()) {
            user.setDeleteFlag(CommonConstant.DELETE_FLAG_FALSE);
        } else {
            user.setDeleteFlag(CommonConstant.DELETE_FLAG_TRUE);
        }
        userRepository.save(user);
        return CommonConstant.DELETE_SUCCESS;
    }

    @Override
    public void resetPassword(String username, String password) {
        User user = userRepository.findByEmailOrUsernameOrMobile(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public void updateUserRole(List<Long> roleIds, Long userId) {
        User user = userRepository.findById(userId).get();
        List<UserRole> currentUserRoles = userRoleRepository.getAllRolesByUserId(userId);

        List<Long> currentRoleIds = new ArrayList<>();

        for (UserRole userRole : currentUserRoles) {
            currentRoleIds.add(userRole.getSetting().getId());
        }

        for (Long id : roleIds) {
            if (currentRoleIds.contains(id)) {
                UserRole userRole = userRoleRepository.getRoleByUserIdAndSettingId(id, user.getId());
                if (!userRole.isActive()) {
                    userRole.setActive(true);
                    userRoleRepository.save(userRole);
                }
                currentRoleIds.remove(id);
            } else {
                // add new user role
                UserRole userRole = new UserRole();
                Setting setting = settingRepository.getById(id);
                userRole.setUser(user);
                userRole.setActive(true);
                userRole.setSetting(setting);
                userRoleRepository.save(userRole);
            }
        }
        for (Long id : currentRoleIds) {
            // change status
            UserRole userRole = userRoleRepository.getRoleByUserIdAndSettingId(id, user.getId());
            userRole.setActive(false);
            userRoleRepository.save(userRole);
        }


    }

    @Override
    public List<User> getAllStaff() {
        List<User> users = new ArrayList<>();
        List<UserRole> userRoles = userRoleRepository.getAllRolesByRoleName("CRDD Staff");
        for(UserRole userRole: userRoles) {
            users.add(userRole.getUser());
        }
        return users;
    }

    @Override
    public List<User> getAllDesigner() {
        return userRepository.findDesigner();
    }

    @Override
    public List<User> getAllReviewer() {
        return userRepository.findReviewer();
    }
}
