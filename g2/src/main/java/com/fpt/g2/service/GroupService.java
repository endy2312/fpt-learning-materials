package com.fpt.g2.service;

import com.fpt.g2.dto.GroupDTO;
import com.fpt.g2.entity.Group;
import com.fpt.g2.entity.Subject;

import java.util.List;

public interface GroupService {
    List<Group> getAllGroupByCurriculum(Long curriculumId);

    void addNewGroup(GroupDTO group);

    List<Subject> getSubjectByGroupAndCurriculum(Long groupId, Long curriculumId);

    Group findById(Long groupId);

    Boolean checkCodeExisted(String code, Long groupId,String type);

    void updateGroup(GroupDTO group);

    void deleteGroup(Long id);
}
