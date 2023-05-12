package com.fpt.g2.service.impl;

import com.fpt.g2.dto.*;
import com.fpt.g2.entity.Curriculum;
import com.fpt.g2.entity.CurriculumSubject;
import com.fpt.g2.entity.Group;
import com.fpt.g2.entity.Subject;
import com.fpt.g2.repository.CurriculumRepository;
import com.fpt.g2.repository.CurriculumSubjectRepository;
import com.fpt.g2.repository.SubjectRepository;
import com.fpt.g2.service.CurriculumSubjectService;
import com.fpt.g2.service.SubjectService;
import com.fpt.g2.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private CurriculumRepository curriculumRepository;

    @Autowired
    private CurriculumSubjectRepository curriculumSubjectRepository;

    @Override
    public Page getAllSubject(SubjectDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize(), dto.getSortBy());
        String keyword = CommonUtils.getEscapeKeyword(dto.getKeyword());
        return subjectRepository.finAllSubject(keyword, dto.getActive(), pageable);
    }

    @Override
    @Transactional
    public void addOrUpdate(SubjectDTO subjectDTO, Long userId) {
        Subject subject = new Subject();
        if (subjectDTO.getId() != null) {
            subject = subjectRepository.findById(subjectDTO.getId()).get();
            if (!Objects.isNull(subjectDTO.getParentId()) && subjectDTO.getParentId() != 0) {
                Subject subjectParent = subjectRepository.findById(subjectDTO.getParentId()).get();
                subjectParent.setIsElective(true);
                subjectRepository.save(subjectParent);
                subject.setParentId(subjectDTO.getParentId());
            } else {
                if (subject.getParentId() != null) {
                    Subject subjectParent = subjectRepository.findById(subject.getParentId()).get();
                    List<Subject> subjects = subjectRepository.findAllByParentIdAndDeleteFlagFalse(subject.getParentId());
                    if (Objects.isNull(subjects) || subjects.size() == 1) {
                        subjectParent.setIsElective(false);
                    }
                }
                subject.setParentId(null);
            }
        }
        subject.setCode(subjectDTO.getCode());
        subject.setDescription(subjectDTO.getDescription());
        subject.setName(subjectDTO.getName());
        subjectRepository.save(subject);
    }


    @Override
    public Boolean isSubjectExisted(String code, Long id, String type) {
        Subject subject = new Subject();
        if (type.equals("add")) {
            subject = subjectRepository.findSubjectByCodeAndDeleteFlagFalse(code);
        }
        if (type.equals("update")) {
            subject = subjectRepository.findSubjectByIdNot(id, code);
        }
        return Objects.isNull(subject) ? false : true;
    }

    @Override
    @Transactional
    public void updateActive(SubjectListDTO subjectList, Long userId) {
        for (Subject subject : subjectList.getSubjectList()) {
            Subject subjectUpdate = subjectRepository.findById(subject.getId()).get();
            subjectUpdate.setDeleteFlag(subject.isDeleteFlag());
            subjectRepository.save(subjectUpdate);
        }
    }

    @Override
    public SubjectUpdateDTO initUpdateSubject(Long id) {
        Subject subject = subjectRepository.findById(id).get();
        SubjectUpdateDTO subjectDto = new SubjectUpdateDTO();
        subjectDto.setActive(subject.isDeleteFlag());
        subjectDto.setName(subject.getName());
        subjectDto.setCode(subject.getCode());
        subjectDto.setId(subject.getId());
        subjectDto.setParentId(subject.getParentId());
        subjectDto.setDescription(subject.getDescription());
        subjectDto.setIsElective(subject.getIsElective());
        List<Subject> subjects = subjectRepository.findAllByIdIsNotAndParentIdIsNull(id);
        subjectDto.setSubjects(subjects);
        return subjectDto;
    }

    @Override
    public List<Subject> findElectiveChildrens(Long electiveParentId) {
        return null;
    }

    @Override
    public void addElectiveSubject(Long parentId, List<Long> subjectIds) {
        for (Long subjectId : subjectIds) {
            Subject subject = subjectRepository.findById(subjectId).get();
            subject.setParentId(parentId);
            subjectRepository.save(subject);
        }
    }

    @Override
    public List<Subject> getAllSubjects() {
        return subjectRepository.getAllSubjects();
    }

    @Override
    public Subject findById(Long parentId) {
        return subjectRepository.findById(parentId).get();
    }

    @Override
    public void addElective(ElectiveDTO elective) {
        Subject subject = new Subject();
        subject.setIsElective(true);
        subject.setCode(elective.getCode());
        subject.setName(elective.getName());
        subject.setDescription(elective.getNote());
        subjectRepository.saveAndFlush(subject);

        Curriculum curriculum = curriculumRepository.findCurriculumById(elective.getCurriculumId());
        CurriculumSubject curriculumSubject = new CurriculumSubject();
        curriculumSubject.setSubject(subject);
        curriculumSubject.setCurriculum(curriculum);
        curriculumSubjectRepository.save(curriculumSubject);
    }

    @Override
    public boolean checkCodeExisted(String code, Long id, String type) {
        List<Subject> subjects = null;
        if (type.equals("update")) {
            subjects = subjectRepository.findAllByIdIsNotAndCodeLike(id, code);
        } else {
            subjects = subjectRepository.findAllByCode(code);
        }
        return subjects == null || subjects.size() == 0 ? false : true;
    }

    @Override
    public void updateCombo(ElectiveDTO elective) {
        Subject subject = subjectRepository.findById(elective.getId()).get();
        subject.setName(elective.getName());
        subject.setCode(elective.getCode());
        subject.setDescription(elective.getNote());
        subjectRepository.save(subject);
    }
    @Override
    public List<Subject> getSubjectsOutsideCurriculum(Long curriculumId) {
        return subjectRepository.getSubjectsOutsideCurriculum(curriculumId);
    }


    @Override
    public void updateCombo(ComboDTO combo) {
        Subject subject = subjectRepository.findById(combo.getId()).get();
        subject.setName(combo.getName());
        subject.setCode(combo.getCode());
        subject.setDescription(combo.getNote());
        subjectRepository.save(subject);
    }

    @Override
    public List<Subject> findByCurriculum(Long curriculumId, Boolean status) {
        return subjectRepository.getComboByCurriculum(curriculumId, status);
    }

    @Override
    public List<Subject> getAllExistedCombo(List<Subject> combos) {
        List<Long> ids = new ArrayList<>();
        for (Subject combo : combos) {
            ids.add(combo.getId());
        }
        return subjectRepository.findExistedCombo(ids);
    }

    @Override
    public List<Subject> getAllSubjectChildrenCombo(Long parentId, Long curriculumId) {
        return subjectRepository.getAllSubjectChildrenCombo(parentId, curriculumId);
    }

    @Override
    public void addCombo(ComboDTO combo) {
        Subject subject = new Subject();
        subject.setIsCombo(true);
        subject.setCode(combo.getCode());
        subject.setName(combo.getName());
        subject.setDescription(combo.getNote());
        subjectRepository.saveAndFlush(subject);

        Curriculum curriculum = curriculumRepository.findCurriculumById(combo.getCurriculumId());
        CurriculumSubject curriculumSubject = new CurriculumSubject();
        curriculumSubject.setSubject(subject);
        curriculumSubject.setCurriculum(curriculum);
        curriculumSubjectRepository.save(curriculumSubject);
    }

    @Override
    public List<Subject> getSubjectNotParent(List<Subject> childrenSubject, Long curriculumId) {
        List<Long> ids = new ArrayList<>();
        for (Subject subject : childrenSubject) {
            ids.add(subject.getId());
        }
        return subjectRepository.findAllNotInIds(ids, curriculumId);
    }


    @Override
    public void removeParentSubject(Long childId) {
        Subject subject = subjectRepository.findById(childId).get();
        subject.setParentId(null);
        subjectRepository.save(subject);
    }

}
