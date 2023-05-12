package com.fpt.g2.service;

import com.fpt.g2.dto.CurriculumRequestDTO;
import com.fpt.g2.dto.PloCustomDTO;
import com.fpt.g2.dto.RequestCommonDTO;
import com.fpt.g2.entity.Curriculum;
import com.fpt.g2.entity.PoPlo;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface CurriculumService {

    Page getAllCurriculum(RequestCommonDTO request,String status, Long staffId);

    String saveOrUpdate(CurriculumRequestDTO curriculum, Long userId);

    Curriculum findById(Long id);

    void changeStatus(Long id, HttpSession session);

    List<PloCustomDTO> getListPloCustom(Long curriculumId);
    List<Curriculum> getListCurriculumByDecisionId(Long decisionId);
}
