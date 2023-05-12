package com.fpt.g2.service.impl;

import com.fpt.g2.dto.ElectiveDTO;
import com.fpt.g2.entity.Elective;
import com.fpt.g2.entity.Subject;
import com.fpt.g2.repository.ElectiveRepository;
import com.fpt.g2.repository.SubjectRepository;
import com.fpt.g2.service.ElectiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ElectiveServiceImpl implements ElectiveService {

    @Autowired
    ElectiveRepository electiveRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Override
    public List<Subject> findByCurriculum(Long curriculumId, Boolean status) {
        return subjectRepository.getElectiveByCurriculum(curriculumId, status);
    }

    @Override
    public List<Subject> getAllSubjectChildren(Long parentId, Long curriculumId) {
        return subjectRepository.getAllSubjectChildren(parentId, curriculumId);
    }

    @Override
    public List<Subject> getAllExistedElective(List<Subject> electives) {
        List<Long> ids = new ArrayList<>();
        for (Subject elective: electives) {
            ids.add(elective.getId());
        }
        return subjectRepository.findAllByIdIsNotInAndDeleteFlagFalse(ids);
    }

    @Override
    public List<Subject> getSubjectNotParent(List<Subject> childrenElective,Long curriculumId) {
        List<Long> ids = new ArrayList<>();
        for (Subject subject : childrenElective) {
            ids.add(subject.getId());
        }
        return subjectRepository.findAllNotInIds(ids,curriculumId);
    }
}
