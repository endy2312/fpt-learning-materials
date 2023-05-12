package com.fpt.g2.utils.user;

import com.fpt.g2.dto.UserDTO;
import com.fpt.g2.dto.UserRequestDTO;
import com.fpt.g2.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserUtils {

    public static UserRequestDTO convertToUserForm(User user) {
        List<Long> userRoleIds = new ArrayList<>();
        UserRequestDTO userRequestDTO = new UserRequestDTO(user.getId(), user.getUsername(), user.getFullName(), user.getEmail(),
                user.getMobile(), user.getPassword(), null, user.getImage(), null, user.getSignupType(),
                user.getTitle(), user.getJobTitle(), user.getOrganization(), userRoleIds,user.isDeleteFlag());
        return userRequestDTO;
    }

    public static UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getFullName(), user.getEmail(),
                user.getMobile(), user.getPassword(), user.getImage(), user.getSignupType(), user.isDeleteFlag());
        return userDTO;
    }
}
