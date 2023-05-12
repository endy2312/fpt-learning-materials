package com.fpt.g2.service.impl;

import com.fpt.g2.dto.CSubjectRequestDTO;
import com.fpt.g2.dto.CSubjectResponseDTO;
import com.fpt.g2.dto.CurriculumSubjectDTO;
import com.fpt.g2.dto.CurriculumSubjectRequestDTO;
import com.fpt.g2.entity.Curriculum;
import com.fpt.g2.entity.CurriculumSubject;
import com.fpt.g2.entity.Group;
import com.fpt.g2.entity.Subject;
import com.fpt.g2.repository.CurriculumRepository;
import com.fpt.g2.repository.CurriculumSubjectRepository;
import com.fpt.g2.repository.GroupRepository;
import com.fpt.g2.repository.SubjectRepository;
import com.fpt.g2.service.CurriculumSubjectService;
import com.fpt.g2.service.PreRequisiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurriculumSubjectServiceImpl implements CurriculumSubjectService {

    @Autowired
    private CurriculumSubjectRepository curriculumSubjectRepository;

    @Autowired
    private PreRequisiteService preRequisiteService;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private CurriculumRepository curriculumRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public List<CSubjectResponseDTO> getCurriculumSubjects(Boolean status, Long curriculumId, CSubjectRequestDTO dto) {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, dto.getSortBy());
        List<CurriculumSubject> subjects = new ArrayList<>();
        if (status) {
            subjects = curriculumSubjectRepository.getCurriculumSubjects(curriculumId, null, pageable);
        } else {
            subjects = curriculumSubjectRepository.getCurriculumSubjects(curriculumId, false, pageable);
        }
        List<CSubjectResponseDTO> responses = new ArrayList<>();
        for (CurriculumSubject sc : subjects) {
            CSubjectResponseDTO response = new CSubjectResponseDTO();
            if (sc.getSubject() != null) {
                response.setId(sc.getId());
                response.setCode(sc.getSubject().getCode());
                response.setSubjectId(sc.getSubject().getId());
                response.setName(sc.getSubject().getName());
                response.setSemester(sc.getSemester());
                response.setNoCredit(sc.getNoCredit());
                response.setIsActive(sc.isDeleteFlag());
                response.setDeleteFlag(sc.isDeleteFlag());
                response.setCreatedDate(sc.getCreatedDate());
                response.setUpdatedDate(sc.getUpdatedDate());
                String predecessor = preRequisiteService.getPreByCurriculumSubject(curriculumId, sc.getSubject().getId());
                response.setPredecessor(predecessor);
                if(sc.getSubjectGroup() != null){
                    response.setGroup(sc.getSubjectGroup().getCode());
                }

                responses.add(response);
            }
        }
        return responses;
    }

    @Override
    public Long changeCurriculumSubjectStatus(Long userId, Long id) {
        CurriculumSubject curriculumSubject = curriculumSubjectRepository.getById(id);
        if (curriculumSubject != null) {
            if (curriculumSubject.isDeleteFlag()) {
                curriculumSubject.setDeleteFlag(false);
            } else {
                curriculumSubject.setDeleteFlag(true);
            }
            curriculumSubject.setUpdatedBy(userId);
            curriculumSubjectRepository.save(curriculumSubject);
            return curriculumSubject.getCurriculum().getId();
        }
        return null;
    }

    @Override
    public List<Subject> getAllElective(Long curriculumId) {
        return curriculumSubjectRepository.getAllElectiveSubject(curriculumId);
    }

    @Override
    public List<Subject> getElectiveForSelect(Long curriculumId, List<Subject> subjects) {
        List<Long> listIdSubject = new ArrayList<>();
        for (Subject subject : subjects) {
            listIdSubject.add(subject.getId());
        }
        return curriculumSubjectRepository.getSubjectNotInIds(curriculumId, listIdSubject);
    }

    @Override
    public void addCurriculumSubject(Long subjectId, Long curriculumId) {
        CurriculumSubject curriculumSubject = new CurriculumSubject();
        curriculumSubject.setCurriculum(curriculumRepository.findCurriculumById(curriculumId));
        curriculumSubject.setSubject(subjectRepository.findById(subjectId).get());
        curriculumSubjectRepository.save(curriculumSubject);
    }

    @Override
    public List<Subject> getAllSubjectByCurriculum(Long curriculumId) {
        return curriculumSubjectRepository.findAllByCurriculum(curriculumId);
    }

    @Override
    public void removeGroupSubject(Long subjectId, Long curriculumId) {
        CurriculumSubject curriculumSubject = curriculumSubjectRepository.getBySubjectAndCurriculum(subjectId,curriculumId);
        curriculumSubject.setSubjectGroup(null);
        curriculumSubjectRepository.save(curriculumSubject);
    }

    @Override
    public List<Subject> getSubjectGroupNotIn(List<Subject> subjects, Long curriculumId) {
        List<Long> ids = new ArrayList<>();
        for (Subject subject: subjects){
            ids.add(subject.getId());
        }
        return curriculumSubjectRepository.getSubjectGroupNotIn(ids,curriculumId);
    }

    @Override
    public void addSubjectForGroup(List<Long> subjectIds, Long curriculumId,Long groupId) {
        List<CurriculumSubject> curriculumSubjects = curriculumSubjectRepository.getAllByCurriculumAndSubjectIds(curriculumId,subjectIds);
        Group group = groupRepository.findById(groupId).get();
        for (CurriculumSubject cs : curriculumSubjects){
            cs.setSubjectGroup(group);
            curriculumSubjectRepository.save(cs);
        }
    }

    @Override
    public List<Subject> getAllCombo(Long curriculumId) {
        return curriculumSubjectRepository.getAllComboSubject(curriculumId);
    }

    @Override
    public List<Group> getGroupsByCurriculum(Long curriculumId) {
        return curriculumSubjectRepository.getGroupsByCurriculum(curriculumId);
    }

    @Override
    public Long updateCurriculumSubjectDetails(CurriculumSubjectRequestDTO request) {
        CurriculumSubject curriculumSubject = curriculumSubjectRepository.getById(request.getCsId());
        Curriculum curriculum = curriculumSubject.getCurriculum();
        Subject subject = curriculumSubject.getSubject();
        Group group = groupRepository.findByCurriculumAndCode(curriculum.getId(), request.getGroup());
        curriculumSubject.setSemester(request.getSemester());
        curriculumSubject.setNoCredit(request.getNoCredit());
        curriculumSubject.setSubjectGroup(group);
        if(request.getStatus().equals("activate")){
            curriculumSubject.setDeleteFlag(false);
        } else {
            curriculumSubject.setDeleteFlag(true);
        }
        curriculumSubjectRepository.save(curriculumSubject);

        if(request.getSubjectIds() != null && request.getSubjectIds().size() > 0){
            preRequisiteService.addNewPredecessor(curriculum, subject, request.getSubjectIds());
        }

        return curriculum.getId();
    }

    @Override
    public void addNewCurriculumSubject(CurriculumSubjectDTO request) {
        CurriculumSubject curriculumSubject = new CurriculumSubject();
        Subject subject = subjectRepository.findSubjectByCode(request.getSubjectCode());
        Curriculum curriculum = curriculumRepository.findCurriculumById(request.getCurriculumId());
        if(request.getGroupCode() != null && !request.getGroupCode().equals("0")) {
            Group group = groupRepository.findByCurriculumAndCode(request.getCurriculumId(), request.getGroupCode());
            curriculumSubject.setSubjectGroup(group);
        }
        curriculumSubject.setSubject(subject);
        curriculumSubject.setCurriculum(curriculum);
        curriculumSubject.setSemester(request.getSemester());
        curriculumSubject.setNoCredit(request.getNoCredit());
        curriculumSubjectRepository.save(curriculumSubject);
    }
}
