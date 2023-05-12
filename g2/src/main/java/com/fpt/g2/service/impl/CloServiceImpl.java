package com.fpt.g2.service.impl;

import com.fpt.g2.constant.URLConstant;
import com.fpt.g2.dto.CLORequestDTO;
import com.fpt.g2.dto.RequestCommonDTO;
import com.fpt.g2.entity.Clo;
import com.fpt.g2.entity.Syllabus;
import com.fpt.g2.repository.CloRepository;
import com.fpt.g2.repository.SyllabusRepository;
import com.fpt.g2.service.CloService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CloServiceImpl implements CloService {

    @Autowired
    private CloRepository cloRepository;

    @Autowired
    private SyllabusRepository syllabusRepository;

    @Override
    public List<Clo> getAllBySyllabus(Long syllabusId, RequestCommonDTO request) {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, request.getSortBy());
        return cloRepository.getAllBySyllabusId(syllabusId, pageable);
    }

    @Override
    public Long deleteCLO(Long id) {
        Clo clo = cloRepository.findCloById(id);
        Long syllabusId = clo.getSyllabus().getId();
        cloRepository.delete(clo);
        return syllabusId;
    }

    @Override
    public Long updateCLO(Clo clo) {
        Clo syllabusClo = cloRepository.findCloById(clo.getId());
        syllabusClo.setDetails(clo.getDetails());
        Long syllabusId = syllabusClo.getSyllabus().getId();
        cloRepository.save(syllabusClo);
        return syllabusId;
    }

    @Override
    public void addNewCLO(CLORequestDTO request) {
        Syllabus syllabus = syllabusRepository.getSyllabusById(request.getSyllabusId());
        Clo clo = new Clo();
        clo.setCode(request.getCloCode());
        clo.setDetails(request.getCloDetails());
        clo.setSyllabus(syllabus);
        cloRepository.save(clo);
    }

    @Override
    public Boolean isCLOExisted(Long id, String code) {
        if(id != null && id > 0 && !Strings.isNullOrEmpty(code)){
            return cloRepository.isCodeExisted(id, code);
        }
        return null;
    }

    @Override
    public List<Clo> getClosBySyllabus(Long syllabusId) {
        return cloRepository.getClosBySyllabusId(syllabusId);
    }
}
