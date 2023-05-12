package com.fpt.g2.service.impl;

import com.fpt.g2.dto.PredecessorListDTO;
import com.fpt.g2.dto.SuccessorListDTO;
import com.fpt.g2.entity.*;
import com.fpt.g2.repository.CurriculumSubjectRepository;
import com.fpt.g2.entity.Curriculum;
import com.fpt.g2.entity.PreRequisite;
import com.fpt.g2.entity.Subject;
import com.fpt.g2.repository.PreRequisiteRepository;
import com.fpt.g2.repository.SubjectRepository;
import com.fpt.g2.service.PreRequisiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PreRequisiteServiceImpl implements PreRequisiteService {
    @Autowired
    PreRequisiteRepository preRequisiteRepository;

    @Autowired
    private SubjectRepository subjectRepository;


    @Autowired
    CurriculumSubjectRepository curriculumSubjectRepository;

    @Override
    public List<PredecessorListDTO> getPredecessor(String code) {
        List<Curriculum> curricula = preRequisiteRepository.getCurriculumInPredecessor(code);
        List<PredecessorListDTO> predecessorList = new ArrayList<>();
        for (Curriculum curriculum : curricula) {
            PredecessorListDTO listDTO = new PredecessorListDTO();
            Map<String, List<Subject>> mapSubPredecessor = new HashMap<>();
            List<Subject> predecessors = preRequisiteRepository.findPredecessorByCode(code, curriculum.getId());
            for (Subject subject : predecessors) {
                List<Subject> subPredecessor = preRequisiteRepository.findPredecessorByCode(subject.getCode(), curriculum.getId());
                mapSubPredecessor.put(subject.getCode(),subPredecessor);
            }
            Syllabus syllabus = curriculumSubjectRepository.findCurriculumAndSyllabus(code, curriculum.getId());
            listDTO.setSyllabus(syllabus);
            listDTO.setSubPredecessor(mapSubPredecessor);
            listDTO.setCurriculum(curriculum);
            listDTO.setCode(code);
            predecessorList.add(listDTO);
        }
        return predecessorList;
    }

    @Override
    public List<SuccessorListDTO> getSuccessor(String code) {
        List<Curriculum> curricula = preRequisiteRepository.getCurriculumInSuccessor(code);
        List<SuccessorListDTO> successorList = new ArrayList<>();
        for (Curriculum curriculum : curricula) {
            SuccessorListDTO listDTO = new SuccessorListDTO();
            Map<String, List<Subject>> mapSubSuccessor = new HashMap<>();
            List<Subject> successors = preRequisiteRepository.findSuccessorByCode(code, curriculum.getId());
            for (Subject subject : successors) {
                List<Subject> subSuccessor = preRequisiteRepository.findSuccessorByCode(subject.getCode(), curriculum.getId());
                mapSubSuccessor.put(subject.getCode(),subSuccessor);
            }
            Syllabus syllabus = curriculumSubjectRepository.findCurriculumAndSyllabus(code, curriculum.getId());
            listDTO.setSyllabus(syllabus);
            listDTO.setSubSuccessor(mapSubSuccessor);
            listDTO.setCurriculum(curriculum);
            listDTO.setCode(code);
            successorList.add(listDTO);
        }
        return successorList;
    }

    @Override
    public String getPreByCurriculumSubject(Long curriculumId, Long subjectId) {
        String predecessor = "";
        List<PreRequisite> preRequisites = preRequisiteRepository.getListPredecessor(curriculumId, subjectId);

        if (preRequisites != null && preRequisites.size() > 0) {
            for (PreRequisite preRequisite : preRequisites) {
                predecessor = predecessor + preRequisite.getPredecessor().getCode() + ", ";
            }
            return predecessor.substring(0, predecessor.length() - 2);
        }
        return null;
    }

    @Override
    public String getPreBySubject(Long subjectId) {
        String predecessor = "";
        List<PreRequisite> preRequisites = preRequisiteRepository.getListPredecessorBySubject(subjectId);

        if (preRequisites != null && preRequisites.size() > 0) {
            for (PreRequisite preRequisite : preRequisites) {
                predecessor = predecessor + preRequisite.getPredecessor().getCode() + ", ";
            }
            return predecessor.substring(0, predecessor.length() - 2);
        }
        return null;
    }

    @Override
    public void addNewPredecessor(Curriculum curriculum, Subject subject, List<Long> subjectIds) {
        List<PreRequisite> preRequisites = preRequisiteRepository.getListPredecessor(curriculum.getId(), subject.getId());
        preRequisiteRepository.deleteAll(preRequisites);
        for(Long id : subjectIds){
            if(!preRequisiteRepository.isPredecessorsExisted(curriculum.getId(), subject.getId(), id)) {
                Subject predecessor = subjectRepository.findSubjectById(id);
                PreRequisite preRequisite = new PreRequisite();
                preRequisite.setSuccessor(subject);
                preRequisite.setPredecessor(predecessor);
                preRequisite.setCurriculum(curriculum);

                preRequisiteRepository.save(preRequisite);
            }
        }
    }
}
