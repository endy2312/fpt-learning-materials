package com.fpt.g2.service;

import com.fpt.g2.dto.SyllabiRequestDTO;
import com.fpt.g2.dto.SyllabusOverviewDTO;
import com.fpt.g2.dto.SyllabusRequestDTO;
import com.fpt.g2.entity.Syllabus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SyllabusService {
    SyllabusOverviewDTO getSyllabusById(Long id);

    Page getAllSyllabus(String status, SyllabiRequestDTO request);

    List<String> getAllStatus();

    void changeSyllabusStatus(Long syllabusId, String status);

    Syllabus getSyllabus(Long id);

    void updateSyllabus(Long id, SyllabusRequestDTO requestDTO);
    List<Syllabus> findAllSyllabusByDecisionId(Long dId);

    List<Syllabus> getSyllabusBySubjectId(Long id);
}
