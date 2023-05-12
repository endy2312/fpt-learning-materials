package com.fpt.g2.service.impl;

import com.fpt.g2.dto.GroupDTO;
import com.fpt.g2.entity.CurriculumSubject;
import com.fpt.g2.entity.Group;
import com.fpt.g2.entity.Subject;
import com.fpt.g2.repository.CurriculumSubjectRepository;
import com.fpt.g2.repository.GroupRepository;
import com.fpt.g2.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private CurriculumSubjectRepository curriculumSubjectRepository;
    @Autowired
    private GroupRepository groupRepository;

    @Override
    public List<Group> getAllGroupByCurriculum(Long curriculumId) {
        return curriculumSubjectRepository.getGroupsByCurriculum(curriculumId);
    }

    @Override
    @Transactional
    public void addNewGroup(GroupDTO groupDto) {
        Group group = new Group();
        group.setCode(groupDto.getCode());
        group.setName(groupDto.getName());
        groupRepository.saveAndFlush(group);
        List<Long> ids = new ArrayList<>();
        for (Long id : groupDto.getSubjectIds()) {
            ids.add(id);
        }
        List<CurriculumSubject> curriculumSubjects = curriculumSubjectRepository.getAllByCurriculumAndSubjectIds(groupDto.getCurriculumId(), ids);

        for (CurriculumSubject cs : curriculumSubjects) {
            cs.setSubjectGroup(group);
            curriculumSubjectRepository.save(cs);
        }
    }

    @Override
    public List<Subject> getSubjectByGroupAndCurriculum(Long groupId, Long curriculumId) {
        return curriculumSubjectRepository.getAllByGroupAndCurriculum(groupId, curriculumId);
    }

    @Override
    public Group findById(Long groupId) {
        return groupRepository.findById(groupId).get();
    }

    @Override
    public Boolean checkCodeExisted(String code, Long groupId,String type) {
        List<Group> groups = null;
        if(type.equals("update")){
            groups= groupRepository.findAllByIdIsNotInAndCodeLike(groupId, code);
        }
        else{
            groups= groupRepository.findAllByCode(code);
        }
        return groups == null || groups.size() == 0 ? false : true;
    }

    @Override
    @Transactional
    public void updateGroup(GroupDTO dto) {
        Group group = groupRepository.findById(dto.getId()).get();
        group.setName(dto.getName());
        group.setCode(dto.getCode());
        groupRepository.save(group);
    }

    @Override
    @Transactional
    public void deleteGroup(Long id) {
        List<CurriculumSubject> curriculumSubjects = curriculumSubjectRepository.findByGroupId(id);
        for (CurriculumSubject cs: curriculumSubjects){
            cs.setSubjectGroup(null);
            curriculumSubjectRepository.save(cs);
        }
        Group group = groupRepository.findById(id).get();
        groupRepository.delete(group);
    }
}
