package com.fpt.g2.service.impl;

import com.fpt.g2.dto.SubjectListPlosDTO;
import com.fpt.g2.dto.SubjectPLOMappingDTO;
import com.fpt.g2.dto.SubjectPloDTO;
import com.fpt.g2.entity.*;
import com.fpt.g2.repository.*;
import com.fpt.g2.service.SubjectPLOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectPLOServiceImpl implements SubjectPLOService {

    @Autowired
    private SubjectPLORepository subjectPLORepository;

    @Autowired
    private CurriculumSubjectRepository curriculumSubjectRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private PloRepository ploRepository;

    @Autowired
    private CurriculumRepository curriculumRepository;

    @Override
    public List<SubjectPLOMappingDTO> getListSubjectPLOs(Long curriculumId) {
        List<SubjectPLOMappingDTO> responses = new ArrayList<>();

        List<Group> groups = curriculumSubjectRepository.getGroupsByCurriculum(curriculumId);
        List<Subject> subjects = subjectPLORepository.getSubjectInSubjectPLO(curriculumId);
        for(Group group : groups){
            SubjectPLOMappingDTO response = new SubjectPLOMappingDTO();
            response.setGroupName(group.getCode() + "_" + group.getName());
            List<SubjectPloDTO> subjectPlos = new ArrayList<>();
            for(Subject subject : subjects){
                if(curriculumSubjectRepository.isSubjectInsideGroup(curriculumId, subject.getId(), group.getId())){
                    SubjectPloDTO subjectPloDTO = new SubjectPloDTO();
                    subjectPloDTO.setSubjectId(subject.getId());
                    subjectPloDTO.setSubjectCode(subject.getCode());
                    List<String> subjectPLOs = new ArrayList<>();
                    List<SubjectPLO> plos = subjectPLORepository.getAllBySubjectAndCurriculum(curriculumId, subject.getId());
                    for(SubjectPLO plo : plos){
                        subjectPLOs.add(plo.getPlo().getName());
                    }
                    subjectPloDTO.setPLOName(subjectPLOs);
                    subjectPlos.add(subjectPloDTO);
                }
            }
            response.setSubjects(subjectPlos);
            responses.add(response);
        }
        return responses;
    }

    @Override
    public void updateSubjectPLOMapping(SubjectListPlosDTO request) {
        Curriculum curriculum = curriculumRepository.findCurriculumById(request.getId());
        for(SubjectPloDTO dto : request.getSubjectPlos()){
            List<SubjectPLO> subjectPLOS = subjectPLORepository.getAllBySubjectCodeAndCurriculumId(dto.getSubjectCode(), request.getId());
            subjectPLORepository.deleteAll(subjectPLOS);
            for(Object ploName : dto.getPlos()){
                Plo plo = ploRepository.findByPloNameAndCurriculumId(ploName.toString(), request.getId());
                Subject subject = subjectRepository.findSubjectByCode(dto.getSubjectCode());
                SubjectPLO subjectPLO = new SubjectPLO();
                subjectPLO.setSubject(subject);
                subjectPLO.setPlo(plo);
                subjectPLO.setCurriculum(curriculum);
                subjectPLORepository.save(subjectPLO);
            }
        }
    }
}
