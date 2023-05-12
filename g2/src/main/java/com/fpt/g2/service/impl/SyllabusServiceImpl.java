package com.fpt.g2.service.impl;

import com.fpt.g2.dto.SyllabiRequestDTO;
import com.fpt.g2.dto.SyllabusOverviewDTO;
import com.fpt.g2.dto.SyllabusRequestDTO;
import com.fpt.g2.entity.Setting;
import com.fpt.g2.entity.Subject;
import com.fpt.g2.entity.Syllabus;
import com.fpt.g2.entity.User;
import com.fpt.g2.repository.SettingRepository;
import com.fpt.g2.repository.SubjectRepository;
import com.fpt.g2.repository.SyllabusRepository;
import com.fpt.g2.repository.UserRepository;
import com.fpt.g2.service.PreRequisiteService;
import com.fpt.g2.service.SyllabusService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SyllabusServiceImpl implements SyllabusService {

    @Autowired
    private SyllabusRepository syllabusRepository;

    @Autowired
    private PreRequisiteService preRequisiteService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SettingRepository settingRepository;

    @Override
    public SyllabusOverviewDTO getSyllabusById(Long id) {
        Syllabus syllabus = syllabusRepository.getSyllabusById(id);
        SyllabusOverviewDTO response = new SyllabusOverviewDTO();
        if (syllabus != null) {
            String predecessors = preRequisiteService.getPreBySubject(syllabus.getSubject().getId());
            response.setSyllabus(syllabus);
            response.setPredecessors(predecessors);
            return response;
        }
        return null;
    }

    @Override
    public Page getAllSyllabus(String status, SyllabiRequestDTO request) {
        String keyword = null;
        if (!Strings.isNullOrEmpty(request.getKeyword())) {
            keyword = "%" + request.getKeyword() + "%";
        }
        if (status == null && request.getStatus() != null && !request.getStatus().equals("All")) {
            status = request.getStatus();
            ;
        }
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), request.getSortBy());
        return syllabusRepository.getAll(status, keyword, pageable);
    }

    @Override
    public List<String> getAllStatus() {
        return syllabusRepository.getAllStatus();
    }

    @Override
    public void changeSyllabusStatus(Long syllabusId, String status) {
        Syllabus syllabus = syllabusRepository.getSyllabusById(syllabusId);
        syllabus.setStatus(status);
        if (status.equals("Approved")) {
            syllabus.setApprovedDate(Timestamp.valueOf(LocalDateTime.now()));
        }
        syllabusRepository.save(syllabus);
    }

    @Override
    public Syllabus getSyllabus(Long id) {
        return syllabusRepository.getSyllabusById(id);
    }

    @Override
    public void updateSyllabus(Long id, SyllabusRequestDTO requestDTO) {
        Setting setting = settingRepository.findByTypeAndTitle("Degree Level", requestDTO.getDegreeLevel());

        Syllabus syllabus = syllabusRepository.getSyllabusById(id);
        syllabus.setName(requestDTO.getName());
        syllabus.setNoCredit(requestDTO.getNoCredit());
        if (setting != null) {
            syllabus.setDegreeLevel(setting);
        }
        User designer = userRepository.findUserByUsernameAndDeleteFlagIsFalse(requestDTO.getDesigner());
        User reviewer = userRepository.findUserByUsernameAndDeleteFlagIsFalse(requestDTO.getReviewer());
        syllabus.setDesigner(designer);
        syllabus.setReviewer(reviewer);
        syllabus.setStudentTasks(requestDTO.getStudentTasks());
        syllabus.setTimeAllocation(requestDTO.getTimeAllocation());
        syllabus.setTools(requestDTO.getTools());
        syllabus.setScoringScale(requestDTO.getScoringScale());
        syllabus.setMinAvg(requestDTO.getMinAvg());
        syllabus.setNote(requestDTO.getNote());
        syllabusRepository.save(syllabus);
    }

    @Override
    public List<Syllabus> findAllSyllabusByDecisionId(Long dId) {
        return syllabusRepository.findAllByDecisionId(dId);
    }

    @Override
    public List<Syllabus> getSyllabusBySubjectId(Long id) {
        return syllabusRepository.findAllBySubject(id);
    }
}
