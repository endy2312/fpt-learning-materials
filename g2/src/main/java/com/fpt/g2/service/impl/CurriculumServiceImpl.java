package com.fpt.g2.service.impl;

import com.fpt.g2.constant.CommonConstant;
import com.fpt.g2.dto.CurriculumRequestDTO;
import com.fpt.g2.dto.PloCustomDTO;
import com.fpt.g2.dto.RequestCommonDTO;
import com.fpt.g2.entity.*;
import com.fpt.g2.repository.*;
import com.fpt.g2.service.CurriculumService;
import com.fpt.g2.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CurriculumServiceImpl implements CurriculumService {

    @Autowired
    private CurriculumRepository curriculumRepository;


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DecisionRepository decisionRepository;
    @Autowired
    private PloRepository ploRepository;
    @Autowired
    private PoRepository poRepository;
    @Autowired
    private PoPloRepository poPloRepository;

    @Override
    public Curriculum findById(Long id) {
        return curriculumRepository.findCurriculumById(id);
    }

    @Override
    public void changeStatus(Long id, HttpSession session) {
        Curriculum curriculum = curriculumRepository.findCurriculumById(id);
        if (curriculum.isActive() == true) {
            curriculum.setActive(false);
        } else {
            curriculum.setActive(true);
        }
        curriculum.setUpdatedBy((Long) session.getAttribute(CommonConstant.ID));
        curriculumRepository.save(curriculum);
    }

    @Override
    public List<PloCustomDTO> getListPloCustom(Long curriculumId) {
        List<Plo> plos = ploRepository.findAllByCurriculumId(curriculumId);
        List<Po> pos = poRepository.findAllByCurriculumId(curriculumId);
        List<PoPlo> ppList = new ArrayList<>();
        for (Po po : pos) {
            for (Plo plo : plos) {
                PoPlo poPlo = poPloRepository.findByPoIdAndPloId(po.getId(), plo.getId());
                if (poPlo != null) {
                    ppList.add(poPlo);
                }
            }
        }

        List<PloCustomDTO> ploCustomDTOS = new ArrayList<>();
        for (Plo plo : plos) {
            PloCustomDTO ploCustomDTO = new PloCustomDTO();
            List<String> posName = new ArrayList<>();
            for (PoPlo poPlo : ppList) {
                if (plo.getId() == poPlo.getPlo().getId()) {
                    posName.add(poPlo.getPo().getName());
                }
            }
            ploCustomDTO.setPlo(plo);
            ploCustomDTO.setPos(posName);
            ploCustomDTOS.add(ploCustomDTO);
        }

        return ploCustomDTOS;
    }

    @Override
    public List<Curriculum> getListCurriculumByDecisionId(Long decisionId) {
        return curriculumRepository.findAllCurriculumsByDecisionId(decisionId);
    }

    @Override
    public Page getAllCurriculum(RequestCommonDTO request, String status, Long staffId) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), request.getSortBy());
        String keyword = request.getKeyword();
        if (!CommonUtils.isNullOrEmpty(request.getKeyword())) {
            keyword = "%" + keyword + "%";
        }
        Page<Curriculum> curriculums;

        if (staffId != null) {
            curriculums = curriculumRepository.findAllByStaffId(keyword, staffId, status == null || status == "" ? null : status.equalsIgnoreCase("Activate"), pageable);
        } else {
            curriculums = curriculumRepository.findAll(keyword, status == null || status == "" ? null : status.equalsIgnoreCase("Activate"), pageable);
        }
        return curriculums;
    }

    @Override
    public String saveOrUpdate(CurriculumRequestDTO curriculumDto, Long userId) {

        Curriculum curriculumByCode = curriculumRepository.findCurriculumByCode(curriculumDto.getCode());

        if (curriculumDto.getId() != null && curriculumByCode != null
                && !Objects.equals(curriculumByCode.getId(), curriculumDto.getId())) {
            return CommonConstant.CODE_EXIST;
        }

        Curriculum curriculum;
        User user;
        if (curriculumDto.getId() != null) {
            curriculum = curriculumRepository.findCurriculumById(curriculumDto.getId());
        } else {
            curriculum = new Curriculum();
        }

        if (curriculumDto.getUserId() != null) {
            user = userRepository.findById(curriculumDto.getUserId()).get();
        } else {
            user = userRepository.findById(userId).get();
        }

        if (curriculumDto.getDecisionId() != null) {
            curriculum.setDecision(decisionRepository.findById(curriculumDto.getDecisionId()).get());
        }

        curriculum.setCode(curriculumDto.getCode().trim());
        curriculum.setName(curriculumDto.getName().trim());
        curriculum.setEnglishName(curriculumDto.getEnglishName().trim());
        curriculum.setDescription(curriculumDto.getDescription().trim());
        curriculum.setTotalCredit(curriculumDto.getTotalCredit());
        curriculum.setActive(curriculumDto.getId() == null ? false : (curriculumDto.getIsActive().equalsIgnoreCase("activate") ? true : false));
        curriculum.setUser(user);
        curriculum.setUpdatedBy(userId);

        curriculumRepository.save(curriculum);
        return curriculumDto.getId() == null ? CommonConstant.ADD_SUCCESS : CommonConstant.UPDATE_SUCCESS;
    }


}
